package com.example.flamespace.buildings

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flamespace.R
import com.example.flamespace.retrofit.RetrofitHelper
import com.example.flamespace.retrofit.Room
import com.example.flamespace.user.Reservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Cma : AppCompatActivity() {

    private lateinit var roomAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cma_room)

        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener { onBackPressed() }


        // Initialize views and set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.cma_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter with an empty list of rooms
//        roomAdapter = RoomAdapter(emptyList())
//        recyclerView.adapter = roomAdapter

        // Fetch rooms grouped by buildings from API
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
                        // Filter rooms for the "PTC" building
                        val cmaRooms = rooms.filter { it.building == "CMA" }
                        // Pass the list of rooms to the adapter
                        roomAdapter.updateData(cmaRooms)
                    }
                } else {
                    // Handle error
                    Toast.makeText(this@Cma, "Failed to fetch rooms", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@Cma, "Network error", Toast.LENGTH_SHORT).show()
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
//override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_cma_room)
//
//        val backButton: FrameLayout = findViewById(R.id.backButton)
//        backButton.setOnClickListener { onBackPressed() }
//
//        findViewById<CardView>(R.id.cma_123).setOnClickListener {
//            showPopup("CMA 123", "40 chairs\n1 air conditioner working")
//        }
//
//        findViewById<CardView>(R.id.cma_124).setOnClickListener {
//            showPopup("CMA 124", "40 chairs\n1 air conditioner working")
//        }
//        findViewById<CardView>(R.id.cma_125).setOnClickListener {
//            showPopup("CMA 125", "40 chairs\n1 air conditioner working")
//        }
//        findViewById<CardView>(R.id.cma_126).setOnClickListener {
//            showPopup("CMA 126", "40 chairs\n1 air conditioner working")
//        }
//        findViewById<CardView>(R.id.cma_127).setOnClickListener {
//            showPopup("CMA 127", "40 chairs\n1 air conditioner working")
//        }
//        findViewById<CardView>(R.id.cma_323).setOnClickListener {
//            showPopup("CMA 323", "40 chairs\n1 air conditioner working")
//        }
//        findViewById<CardView>(R.id.cma_324).setOnClickListener {
//            showPopup("CMA 324", "40 chairs\n1 air conditioner working")
//        }
//        findViewById<CardView>(R.id.cma_324).setOnClickListener {
//            showPopup("CMA 324", "40 chairs\n1 air conditioner working")
//        }
//        findViewById<CardView>(R.id.cma_325).setOnClickListener {
//            showPopup("CMA 325", "40 chairs\n1 air conditioner working")
//        }
//        findViewById<CardView>(R.id.cma_326).setOnClickListener {
//            showPopup("CMA 326", "40 chairs\n1 air conditioner working")
//        }
//        findViewById<CardView>(R.id.cma_327).setOnClickListener {
//            showPopup("CMA 327", "40 chairs\n1 air conditioner working")
//        }
//
//
//
//    }
//
//    private fun navigateToReservationActivity(roomCode: String) {
//        val intent = Intent(this, Reservation::class.java)
//        intent.putExtra("ROOM_CODE", roomCode)
//        startActivity(intent)
//    }
//
//    private fun showPopup(roomCode: String, roomDetails: String) {
//        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val popupView = inflater.inflate(R.layout.fragment_modal_popup, null)
//
//        val width = LinearLayout.LayoutParams.WRAP_CONTENT
//        val height = LinearLayout.LayoutParams.WRAP_CONTENT
//        val focusable = true
//
//        val popupWindow = PopupWindow(popupView, width, height, focusable)
//
//        val roomCodeTextView = popupView.findViewById<android.widget.TextView>(R.id.roomCodeTextView)
//        val roomDetailsTextView = popupView.findViewById<android.widget.TextView>(R.id.roomDetailsTextView)
//        val reserveButton = popupView.findViewById<android.widget.Button>(R.id.reserveButton)
//        val btnCancel = popupView.findViewById<android.widget.Button>(R.id.cancelButton)
//
//        roomCodeTextView.text = roomCode
//        roomDetailsTextView.text = roomDetails
//
//        reserveButton.setOnClickListener {
//            navigateToReservationActivity(roomCode)
//            popupWindow.dismiss()
//        }
//
//        btnCancel.setOnClickListener {
//            popupWindow.dismiss()
//        }
//
//        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)
//    }