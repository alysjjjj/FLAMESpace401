package com.example.flamespace.user

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flamespace.R
import com.example.flamespace.buildings.Home
import com.example.flamespace.retrofit.SharedPreferencesManager
import com.google.firebase.database.*
import okhttp3.*
import java.io.IOException

class SignIn : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var etPhone: EditText  // New phone field

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Initialize Firebase reference
        database = FirebaseDatabase.getInstance("https://flames-a63e3-default-rtdb.firebaseio.com/").reference.child("users")

        val etEmail = findViewById<EditText>(R.id.et_email)
        etPhone = findViewById(R.id.et_phone) // Ensure this exists in your layout
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val tvHaventAccount = findViewById<TextView>(R.id.tv_havent_account)
        val tvForgotPw = findViewById<TextView>(R.id.tv_forgot_pw)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showToast("Please fill in all required fields")
                return@setOnClickListener
            }

            loginUserXampp(email, phone, password)
        }
        tvHaventAccount.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }
        tvForgotPw.setOnClickListener {
            startActivity(Intent(this, ForgotActivity::class.java))
        }
    }

    private fun loginUserXampp(email: String, phone: String, password: String) {
        val client = OkHttpClient()
        val formBody = FormBody.Builder()
            .add("email", email)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url("http://192.168.1.7/flames/login.php") // Update with your actual URL
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    showToast("Error connecting to server: ${e.message}")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                runOnUiThread {
                    if (response.isSuccessful && responseBody == "Login successful") {
                        loginUserFirebase(email, phone, password)
                    } else {
                        showToast("Login failed: $responseBody")
                    }
                }
            }
        })
    }

    private fun loginUserFirebase(email: String, phone: String, password: String) {
        database.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnap in snapshot.children) {
                            val storedPassword = userSnap.child("password").getValue(String::class.java)
                            val userId = userSnap.child("id").getValue(String::class.java)
                            val username = userSnap.child("name").getValue(String::class.java)
                            val storedPhone = userSnap.child("phone").getValue(String::class.java)

                            if (storedPassword == password) {
                                // If the phone is not stored and a phone number is provided, update it
                                if ((storedPhone == null || storedPhone.isEmpty()) && phone.isNotEmpty()) {
                                    database.child(userSnap.key!!).child("phone").setValue(phone)
                                }
                                SharedPreferencesManager.saveUserDetails(
                                    this@SignIn, userId ?: "", username ?: "", email
                                )
                                navigateToVerification(userId ?: "")
                                return
                            } else {
                                showToast("Invalid password")
                                return
                            }
                        }
                    } else {
                        showToast("User not found")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    showToast("Database error: ${error.message}")
                }
            })
    }

    private fun navigateToVerification(userId: String) {
        showToast("Login successful! Redirecting to OTP verification.")
        val intent = Intent(this, LoginVerification::class.java)
        intent.putExtra("userId", userId)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
