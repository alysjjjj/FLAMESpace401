package com.example.flamespace.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flamespace.R

class HistoryAdapter(private val historyList: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.room_recycle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = historyList[position]
        holder.roomTextView.text = item.room
        holder.timeTextView.text = item.time
        holder.dateTextView.text = item.date
    }

    override fun getItemCount(): Int = historyList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val roomTextView: TextView = itemView.findViewById(R.id.tv_room)
        val timeTextView: TextView = itemView.findViewById(R.id.history_time)
        val dateTextView: TextView = itemView.findViewById(R.id.history_date)
    }
}
