package com.example.flamespace.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.cardview.widget.CardView
import com.example.flamespace.R
import com.example.flamespace.user.MainActivity

class Profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val profileCardView = findViewById<CardView>(R.id.profile_cardview)
        val newName = intent.getStringExtra("newName")
        val newEmail = intent.getStringExtra("newEmail")


        val usernameTextView: android.widget.TextView = findViewById(R.id.Username)
        if (!newName.isNullOrEmpty()) {
            usernameTextView.text = newName
        }

        val emailTextView: android.widget.TextView = findViewById(R.id.email_prf)
        if (!newEmail.isNullOrEmpty()) {
            emailTextView.text = newEmail
        }

        val editCardView = profileCardView.findViewById<CardView>(R.id.edit_cv)
        editCardView.setOnClickListener {
            val intent = Intent(this, Edit_profile::class.java)
            startActivity(intent)
        }

        val currentCardView = profileCardView.findViewById<CardView>(R.id.current_cv)
        currentCardView.setOnClickListener {
            val intent = Intent(this, Current::class.java)
            startActivity(intent)
        }


        val backButton = findViewById<FrameLayout>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val logoutbtn = findViewById<android.widget.Button>(R.id.buttonLogout)
        logoutbtn.setOnClickListener {
            showLogoutPopup()
        }
    }

    private fun showLogoutPopup() {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.activity_logout, null)

        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true

        val popupWindow = PopupWindow(popupView, width, height, focusable)

        val btnCancelLogout = popupView.findViewById<android.widget.Button>(R.id.btnCancel)
        btnCancelLogout.setOnClickListener {
            popupWindow.dismiss()
        }

        val btnConfirmLogout = popupView.findViewById<android.widget.Button>(R.id.btnLogout)
        btnConfirmLogout.setOnClickListener {
            logout()
            popupWindow.dismiss()
        }

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)
    }

    private fun logout() {
        clearSessionData()
        navigateToLoginScreen()
    }

    private fun clearSessionData() {
        // Clear session data logic
    }

    private fun navigateToLoginScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}