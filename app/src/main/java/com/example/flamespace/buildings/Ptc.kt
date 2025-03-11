package com.example.flamespace.buildings

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flamespace.R
import com.example.flamespace.retrofit.RetrofitHelper
import com.example.flamespace.retrofit.Room
import android.widget.Toast
import com.example.flamespace.user.Reservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Ptc : AppCompatActivity() {

    private lateinit var roomAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener { onBackPressed() }

        val recyclerView = findViewById<RecyclerView>(R.id.ptc_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        roomAdapter = RoomAdapter(emptyList()) { room ->
            navigateToReservation(room)
        }
        recyclerView.adapter = roomAdapter

        fetchRoomsGroupedByBuilding()
    }

    private fun fetchRoomsGroupedByBuilding() {
        val apiService = RetrofitHelper.getService()
        val call = apiService.getRooms()

        call.enqueue(object : Callback<List<Room>> {
            override fun onResponse(call: Call<List<Room>>, response: Response<List<Room>>) {
                if (response.isSuccessful) {
                    val rooms = response.body()
                    if (rooms != null) {
                        val ptcRooms = rooms.filter { it.building == "PTC" }
                        roomAdapter.updateData(ptcRooms)
                    }
                } else {
                    Toast.makeText(this@Ptc, "Failed to fetch rooms", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                Toast.makeText(this@Ptc, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToReservation(room: Room) {
        val intent = Intent(this, Reservation::class.java).apply {
            putExtra("ROOM_CODE", room.roomNumber) // Pass room details to Reservation activity
        }
        startActivity(intent)
    }
}
