package com.example.flamespace.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.flamespace.R

class Logout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_logout)

        val btnLogout = findViewById<android.widget.Button>(R.id.btnLogout)
        val btnCancel = findViewById<android.widget.Button>(R.id.btnCancel)

        btnLogout.setOnClickListener {
            logout()
        }

        btnCancel.setOnClickListener {
            finish()
        }

    }

    private fun logout() {
        clearSessionData()
        navigateToLoginScreen()
    }

    private fun clearSessionData() {
        val sharedPreferences = getSharedPreferences("YourSessionData", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            clear()
            apply()
        }
    }

    private fun navigateToLoginScreen() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
        finish()
    }


}
