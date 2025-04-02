package com.example.flamespace.user

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.flamespace.R
import com.example.flamespace.buildings.Home
import com.google.firebase.database.FirebaseDatabase
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class LoginVerification : AppCompatActivity() {

    // Use your Flamespace Firebase URL here
    private val database = FirebaseDatabase.getInstance("https://flamespace-590f0-default-rtdb.firebaseio.com/").reference
    private var generatedOtp: String = ""
    private lateinit var userPhoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otpverification)

        val otpEditText = findViewById<EditText>(R.id.et_otp)
        val verifyButton = findViewById<Button>(R.id.btn_verify_otp)
        val cancelTextView = findViewById<TextView>(R.id.tv_cancel)


        val userId = intent.getStringExtra("userId")
        if (!userId.isNullOrEmpty()) {
            fetchUserPhoneNumber(userId)
        } else {
            Toast.makeText(this, "User ID not found!", Toast.LENGTH_LONG).show()
            finish()
        }

        otpEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val isValid = s?.length == 6
                verifyButton.isEnabled = isValid
                verifyButton.backgroundTintList = ContextCompat.getColorStateList(
                    this@LoginVerification,
                    if (isValid) R.color.enabled_button_color else android.R.color.darker_gray
                )
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        verifyButton.setOnClickListener {
            verifyOtp(otpEditText.text.toString())
        }

        cancelTextView.setOnClickListener {
            finish()
        }
    }

    private fun fetchUserPhoneNumber(userId: String) {
        database.child("users").child(userId).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    userPhoneNumber = snapshot.child("phone").value.toString()
                    sendOtp(userPhoneNumber)
                } else {
                    Toast.makeText(this, "User not found in database!", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error fetching user data: ${e.message}", Toast.LENGTH_LONG).show()
                finish()
            }
    }

    private fun sendOtp(phone: String) {
        // Format the phone number to international format if needed
        val formattedPhone = when {
            phone.startsWith("+63") -> phone
            phone.startsWith("09") -> "+63" + phone.substring(1)
            else -> phone
        }

        Log.d("Twilio", "Sending OTP to: $formattedPhone")

        generatedOtp = (100000..999999).random().toString()

        // Replace these with your actual Twilio credentials
        val twilioAccountSid = "AC83de4da26dd06ada4f41803e130c4dd9"
        val twilioAuthToken = "c85e2fccd6f476856d80209d5610359e"
        val messagingServiceSid = "MGb3286812337e124dde690879a55181cf"

        val client = OkHttpClient()

        val requestBody = FormBody.Builder()
            .add("To", formattedPhone)
            .add("MessagingServiceSid", messagingServiceSid)
            .add("Body", "Your Flamespace OTP is: $generatedOtp")
            .build()

        val request = Request.Builder()
            .url("https://api.twilio.com/2010-04-01/Accounts/$twilioAccountSid/Messages.json")
            .post(requestBody)
            .header("Authorization", Credentials.basic(twilioAccountSid, twilioAuthToken))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@LoginVerification, "Failed to send OTP: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@LoginVerification, "OTP sent to $formattedPhone", Toast.LENGTH_LONG).show()
                        Log.d("OTP", "Generated OTP: $generatedOtp")
                    } else {
                        Toast.makeText(this@LoginVerification, "Failed to send OTP: ${response.code}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun verifyOtp(enteredOtp: String) {
        if (enteredOtp == generatedOtp) {
            Toast.makeText(this, "OTP Verified!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Incorrect OTP, please try again.", Toast.LENGTH_LONG).show()
        }
    }
}