package com.example.flamespace.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.flamespace.R

class ChangePassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_change_password)


        val backButton = findViewById<FrameLayout>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }


        val btnsave: Button = findViewById(R.id.save_pass)
        btnsave.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
    }
}