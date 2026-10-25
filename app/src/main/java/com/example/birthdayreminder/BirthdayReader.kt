package com.example.birthdayreminder

import android.content.Context
import android.provider.ContactsContract
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BirthdayReader(private val context: Context) {

    // Reads all contact birthdays through the Contacts Content Provider
    fun readBirthdays(): List<BirthdayItem> {
        val result = mutableListOf<Pair<Int, BirthdayItem>>()

        val projection = arrayOf(
            ContactsContract.Data.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Event.START_DATE
        )

        // Only rows of type "Event" whose event type is "Birthday"
        val selection = "${ContactsContract.Data.MIMETYPE} = ? AND " +
                "${ContactsContract.CommonDataKinds.Event.TYPE} = ?"
        val selectionArgs = arrayOf(
            ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY.toString()
        )

        val cursor = context.contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {
            val nameIndex = it.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)
            val dateIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE)

            while (it.moveToNext()) {
                val name = it.getString(nameIndex) ?: continue
                val rawDate = it.getString(dateIndex) ?: continue

                val parsed = parseDate(rawDate) ?: continue
                val month = parsed.first
                val day = parsed.second

                val daysLeft = daysUntil(month, day)
                val item = BirthdayItem(
                    name = name,
                    dateText = formatDate(month, day),
                    daysLeftText = daysLeftText(daysLeft)
                )
                result.add(Pair(daysLeft, item))
            }
        }

        // Nearest upcoming birthday first
        return result.sortedBy { it.first }.map { it.second }
    }

    // Handles both "yyyy-MM-dd" and "--MM-dd" (birthday saved without a year)
    private fun parseDate(raw: String): Pair<Int, Int>? {
        val parts = raw.split("-").filter { it.isNotEmpty() }
        if (parts.size < 2) return null

        val month = parts[parts.size - 2].toIntOrNull() ?: return null
        val day = parts.last().take(2).toIntOrNull() ?: return null

        if (month !in 1..12 || day !in 1..31) return null
        return Pair(month, day)
    }

    // Number of days from today to the next occurrence of this birthday
    private fun daysUntil(month: Int, day: Int): Int {
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val next = today.clone() as Calendar
        next.set(Calendar.MONTH, month - 1)
        next.set(Calendar.DAY_OF_MONTH, day)

        if (next.before(today)) {
            next.add(Calendar.YEAR, 1)
        }

        val diff = next.timeInMillis - today.timeInMillis
        return Math.round(diff / 86400000.0).toInt()
    }

    private fun formatDate(month: Int, day: Int): String {
        val cal = Calendar.getInstance()
        cal.set(Calendar.MONTH, month - 1)
        cal.set(Calendar.DAY_OF_MONTH, day)
        return SimpleDateFormat("dd MMM", Locale.getDefault()).format(cal.time)
    }

    private fun daysLeftText(days: Int): String = when (days) {
        0 -> "Today!"
        1 -> "Tomorrow"
        else -> "in $days days"
    }
}