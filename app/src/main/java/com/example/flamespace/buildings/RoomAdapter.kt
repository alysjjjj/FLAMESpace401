package com.example.flamespace.buildings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.flamespace.R
import com.example.flamespace.retrofit.Room

class RoomAdapter(private var rooms: List<Room>, private val onItemClick: (Room) -> Unit) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rooms_rv, parent, false)
        return RoomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = rooms[position]
        holder.bind(room)
        holder.itemView.setOnClickListener {
            onItemClick(room)
        }
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    inner class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: CardView = itemView.findViewById(R.id.cardView)
        private val roomNumberTextView: TextView = itemView.findViewById(R.id.roomNumberTextView)

        fun bind(room: Room) {
            val roomNumber = "${room.building} ${room.roomNumber}"
            roomNumberTextView.text = roomNumber

            cardView.setOnClickListener {
                onItemClick(room)
            }
        }
    }

    fun updateData(newRooms: List<Room>) {
        rooms = newRooms
        notifyDataSetChanged()
    }
}
