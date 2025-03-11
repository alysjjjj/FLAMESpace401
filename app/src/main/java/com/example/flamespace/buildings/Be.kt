package com.example.flamespace.buildings


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flamespace.R
import com.example.flamespace.retrofit.RetrofitHelper
import com.example.flamespace.retrofit.Room
import com.example.flamespace.user.Reservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Be : AppCompatActivity() {

    private lateinit var roomAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_be)

        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener { onBackPressed() }


        // Initialize views and set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.be_recycler)
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
                        val beRooms = rooms.filter { it.building == "BE" }
                        // Pass the list of rooms to the adapter
                        roomAdapter.updateData(beRooms)
                    }
                } else {
                    // Handle error
                    Toast.makeText(this@Be, "Failed to fetch rooms", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@Be, "Network error", Toast.LENGTH_SHORT).show()
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

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_be)
//
//        val backButton = findViewById<FrameLayout>(R.id.backButton)
//        backButton.setOnClickListener { finish() }
//
//        findViewById<CardView>(R.id.be_121).setOnClickListener {
//            showPopup("BE 121", "40 chairs\n1 air conditioner working")
//        }
//        findViewById<CardView>(R.id.be_122).setOnClickListener {
//            showPopup("BE 122", "50 chairs\nno air conditioner working")
//        }
//
//        findViewById<CardView>(R.id.be_123).setOnClickListener {
//            showPopup("BE 123", "60 chairs\n3 air conditioner working")
//        }
//
//        findViewById<CardView>(R.id.be_124).setOnClickListener {
//            showPopup("BE 124", "57 chairs\n2 air conditioner working")
//        }
//
//        findViewById<CardView>(R.id.be_221).setOnClickListener {
//            showPopup("BE 221", "44 chairs\n2 air conditioner working")
//        }
//
//        findViewById<CardView>(R.id.be_222).setOnClickListener {
//            showPopup("BE 128", "49 chairs\n2 air conditioner working")
//        }
//        findViewById<CardView>(R.id.be_223).setOnClickListener {
//            showPopup("BE 222", "52 chairs\n2 air conditioner working")
//        }
//
//        findViewById<CardView>(R.id.be_224).setOnClickListener {
//            showPopup("BE 224", "50 chairs\n2 air conditioner working")
//        }
//
//        findViewById<CardView>(R.id.be_321).setOnClickListener {
//            showPopup("BE 321", "45 chairs\n2 air conditioner working")
//        }
//
//        findViewById<CardView>(R.id.be_322).setOnClickListener {
//            showPopup("BE 322", "55 chairs\n2 air conditioner working")
//        }
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