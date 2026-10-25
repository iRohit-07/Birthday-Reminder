package com.example.birthdayreminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BirthdayAdapter(private var items: List<BirthdayItem>) :
    RecyclerView.Adapter<BirthdayAdapter.BirthdayViewHolder>() {

    class BirthdayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvInitial: TextView = view.findViewById(R.id.tvInitial)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvDate: TextView = view.findViewById(R.id.tvDate)
        val tvDaysLeft: TextView = view.findViewById(R.id.tvDaysLeft)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirthdayViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_birthday, parent, false)
        return BirthdayViewHolder(view)
    }

    override fun onBindViewHolder(holder: BirthdayViewHolder, position: Int) {
        val item = items[position]
        holder.tvInitial.text = item.name.firstOrNull()?.uppercase() ?: "?"
        holder.tvName.text = item.name
        holder.tvDate.text = item.dateText
        holder.tvDaysLeft.text = item.daysLeftText
    }

    override fun getItemCount(): Int = items.size

    // Replaces the list and refreshes the screen
    fun updateData(newItems: List<BirthdayItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}