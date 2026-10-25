package com.example.birthdayreminder

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: BirthdayAdapter
    private lateinit var tvSubtitle: TextView

    // Handles the result of the permission pop-up
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        val readGranted = results[Manifest.permission.READ_CONTACTS] == true
        if (readGranted) {
            loadBirthdays(showToast = true)
        } else {
            Toast.makeText(this, "Permission denied. Cannot read contacts.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Keep content below the status bar and above the navigation bar
        val mainView = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                bars.left + 16.dp(),
                bars.top + 16.dp(),
                bars.right + 16.dp(),
                bars.bottom + 16.dp()
            )
            insets
        }

        tvSubtitle = findViewById(R.id.tvSubtitle)

        // Set up the (initially empty) list
        val rvBirthdays = findViewById<RecyclerView>(R.id.rvBirthdays)
        rvBirthdays.layoutManager = LinearLayoutManager(this)
        adapter = BirthdayAdapter(emptyList())
        rvBirthdays.adapter = adapter

        findViewById<Button>(R.id.btnLoad).setOnClickListener {
            if (hasPermission(Manifest.permission.READ_CONTACTS)) {
                loadBirthdays(showToast = true)
            } else {
                requestContactPermissions()
            }
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            showNameDialog()
        }
    }

    // Refresh the list when coming back from the Contacts editor
    override fun onResume() {
        super.onResume()
        if (hasPermission(Manifest.permission.READ_CONTACTS)) {
            loadBirthdays(showToast = false)
        }
    }

    private fun hasPermission(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    private fun requestContactPermissions() {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS
            )
        )
    }

    // Step 1 of adding: ask for the name
    private fun showNameDialog() {
        val input = EditText(this)
        input.hint = "Contact name"

        val container = FrameLayout(this)
        container.setPadding(24.dp(), 16.dp(), 24.dp(), 0)
        container.addView(input)

        AlertDialog.Builder(this)
            .setTitle("Add Birthday")
            .setView(container)
            .setPositiveButton("Next") { _, _ ->
                val name = input.text.toString().trim()
                if (name.isEmpty()) {
                    Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
                } else {
                    showDatePicker(name)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Step 2 of adding: pick the date, then open the Contacts editor
    private fun showDatePicker(name: String) {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, day ->
                val intent = BirthdayWriter().createInsertIntent(name, year, month + 1, day)
                try {
                    startActivity(intent)
                    Toast.makeText(this, "Tap Save in the Contacts screen", Toast.LENGTH_LONG).show()
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this, "No Contacts app found on this device", Toast.LENGTH_LONG).show()
                }
            },
            cal.get(Calendar.YEAR) - 20,
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Reads birthdays from the Contacts Content Provider and shows them
    private fun loadBirthdays(showToast: Boolean) {
        val birthdays = BirthdayReader(this).readBirthdays()
        adapter.updateData(birthdays)

        if (birthdays.isEmpty()) {
            tvSubtitle.text = "No birthdays found in contacts"
            if (showToast) {
                Toast.makeText(this, "No birthdays found. Tap Add Birthday to create one.", Toast.LENGTH_LONG).show()
            }
        } else {
            tvSubtitle.text = "${birthdays.size} birthdays found"
        }
    }

    // Converts dp to pixels
    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()
}