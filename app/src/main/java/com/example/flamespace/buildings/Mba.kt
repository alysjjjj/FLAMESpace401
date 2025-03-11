package com.example.flamespace.buildings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flamespace.R
import com.example.flamespace.retrofit.RetrofitHelper
import com.example.flamespace.retrofit.Room
import com.example.flamespace.user.Reservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Mba : AppCompatActivity(){

    private lateinit var roomAdapter: RoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mba_room)

        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener { onBackPressed() }


        // Initialize views and set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.mba_recycler)
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
                        val mbaRooms = rooms.filter { it.building == "MBA" }
                        // Pass the list of rooms to the adapter
                        roomAdapter.updateData(mbaRooms)
                    }
                } else {
                    // Handle error
                    Toast.makeText(this@Mba, "Failed to fetch rooms", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@Mba, "Network error", Toast.LENGTH_SHORT).show()
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
//        setContentView(R.layout.activity_mba_room)
//
//
//        val backButton: FrameLayout = findViewById(R.id.backButton)
//        backButton.setOnClickListener { onBackPressed() }
//
//        findViewById<CardView>(R.id.mba_307).setOnClickListener {
//            showPopup("MBA 307", "40 chairs\n1 air conditioner working")
//        }
//        findViewById<CardView>(R.id.mba_308).setOnClickListener {
//            showPopup("MBA 308", "50 chairs\nno air conditioner working")
//        }
//
//        findViewById<CardView>(R.id.mba_309).setOnClickListener {
//            showPopup("MBA 309", "60 chairs\n3 air conditioner working")
//        }
//
//        findViewById<CardView>(R.id.mba_310).setOnClickListener {
//            showPopup("MBA 310", "57 chairs\n2 air conditioner working")
//        }
//
//        findViewById<CardView>(R.id.mba_405).setOnClickListener {
//            showPopup("MBA 405", "44 chairs\n2 air conditioner working")
//        }
//
//        findViewById<CardView>(R.id.mba_406).setOnClickListener {
//            showPopup("MBA 406", "49 chairs\n2 air conditioner working")
//        }
//        findViewById<CardView>(R.id.mba_407).setOnClickListener {
//            showPopup("MBA 407", "52 chairs\n2 air conditioner working")
//        }
//
//        findViewById<CardView>(R.id.mba_504).setOnClickListener {
//            showPopup("MBA 504", "50 chairs\n2 air conditioner working")
//        }
//
//        findViewById<CardView>(R.id.mba_505).setOnClickListener {
//            showPopup("MBA 505", "45 chairs\n2 air conditioner working")
//        }
//
//        findViewById<CardView>(R.id.mba_506).setOnClickListener {
//            showPopup("MBA 506", "55 chairs\n2 air conditioner working")
//        }
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
//
//    override fun onClick(v: View?) {
//
//    }