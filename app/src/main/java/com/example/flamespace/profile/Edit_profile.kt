package com.example.flamespace.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.flamespace.R

class Edit_profile : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val backButton = findViewById<FrameLayout>(R.id.backButton)
        backButton.setOnClickListener {
            finish() // This will close the current activity and go back to the previous one
        }

        nameEditText = findViewById(R.id.name)
        emailEditText = findViewById(R.id.email)
        buttonSave = findViewById(R.id.buttonsave)

        loadProfileData()

        buttonSave.setOnClickListener {
            val newName = nameEditText.text.toString()
            val newEmail = emailEditText.text.toString()

            saveProfileData(newName, newEmail)

            val intent = Intent(this@Edit_profile, Profile::class.java)
            intent.putExtra("newName", newName)
            intent.putExtra("newEmail", newEmail)
            startActivity(intent)
        }

        val btnChangePass: Button = findViewById(R.id.btn_changepass)
        btnChangePass.setOnClickListener {
            val intent = Intent(this, ChangePassword::class.java)
            startActivity(intent)
        }
    }

    private fun loadProfileData() {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val profileName = sharedPreferences.getString("name", "")
        val email = sharedPreferences.getString("email", "")

        nameEditText.setText(profileName)
        emailEditText.setText(email)
    }

    private fun saveProfileData(name: String, email: String) {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("email", email)
        editor.apply()
    }
}
