package com.example.flamespace.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.example.flamespace.R
import com.example.flamespace.buildings.Home

class Current : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current)

        val buttonClick = findViewById<Button>(R.id.cancel_doneButton)
        buttonClick.setOnClickListener {
            //CANCEL RESERVATION FUNCTION HERE AH
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        val backButton: FrameLayout = findViewById(R.id.backButton)
        backButton.setOnClickListener { onBackPressed() }

        val roomCode = intent.getStringExtra("ROOM_CODE")
        val selectedTime = intent.getStringExtra("SELECTED_TIME")
        val selectedTime2 = intent.getStringExtra("SELECTED_TIME_2")
        val subject = intent.getStringExtra("SUBJECT")


        val roomCodeTextView = findViewById<TextView>(R.id.current_room)
        roomCodeTextView.text = roomCode


        val timeTextView = findViewById<TextView>(R.id.current_schedule)
        timeTextView.text = selectedTime

        val timeTextView2 = findViewById<TextView>(R.id.current_schedule2)
        timeTextView2.text = selectedTime2


        val subjectTextView = findViewById<TextView>(R.id.current_subject)
        subjectTextView.text = subject


        displayRoomDetails(roomCode ?: "")

    }

    private fun displayRoomDetails(roomCode: String) {

        val roomCodeTextView = findViewById<TextView>(R.id.current_room)

        roomCodeTextView.text = roomCode
    }
}
