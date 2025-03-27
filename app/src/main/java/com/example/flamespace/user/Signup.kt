package com.example.flamespace.user

import android.content.Intent
import android.os.Bundle
import android.text.Spanned
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flamespace.buildings.Home
import com.example.flamespace.databinding.ActivitySignUpBinding
import com.example.flamespace.retrofit.User
import com.google.firebase.database.FirebaseDatabase
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import okhttp3.FormBody


class Signup : AppCompatActivity() {
    private var binding: ActivitySignUpBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val etSignupEmail: EditText = binding!!.etSignupEmail

        binding!!.btnSignup.setOnClickListener { v -> registerUser() }

        binding!!.tvHaveAccount.setOnClickListener { v ->
            startActivity(Intent(this, SignIn::class.java))
            finish()
        }
    }

    private fun registerUser() {
        val name = binding!!.etName.text.toString().trim()
        val email = binding!!.etSignupEmail.text.toString().trim()
        val password = binding!!.etSignupPassword.text.toString().trim()
        val confirmPassword = binding!!.etSignupRepassword.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("Please fill in all fields")
            return
        }

        if (password != confirmPassword) {
            showToast("Passwords do not match")
            binding!!.etSignupPassword.text!!.clear()
            binding!!.etSignupRepassword.text!!.clear()
            return
        }

        sendRegistrationToXampp(name, email, password)
    }

    private fun sendRegistrationToXampp(name: String, email: String, password: String) {
        val client = OkHttpClient()
        val formBody: RequestBody = FormBody.Builder()
            .add("name", name)
            .add("email", email)
            .add("password", password)
            .build()

        val request: Request = Request.Builder()
            .url("http://192.168.1.7/flames/register.php")
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread { showToast("Error connecting to server: " + e.message) }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string() ?: ""
                Log.d("RegisterDebug", "Server Response: $responseBody")

                runOnUiThread {
                    if (response.isSuccessful && responseBody.trim() == "Registration successful") {
                        saveUserToFirebase(name, email, password)
                    } else {
                        showToast("Registration failed: $responseBody")
                    }
                }
            }
        })
    }


    private fun saveUserToFirebase(name: String, email: String, password: String) {
        val database =
            FirebaseDatabase.getInstance("https://flames-a63e3-default-rtdb.firebaseio.com/")
        val userId = database.getReference("users").push().key

        if (userId == null) {
            Log.e("Firebase", "User ID is null, skipping Firebase save")
            return
        }

        database.getReference("users").child(userId).setValue(User(userId, name, email, password))
            .addOnSuccessListener { aVoid: Void? ->
                Log.d("Firebase", "User saved successfully")
                navigateToHome()
            }
            .addOnFailureListener { e: Exception ->
                Log.e(
                    "Firebase",
                    "Error saving user: " + e.message
                )
            }
    }

    private fun navigateToHome() {
        showToast("Registered successfully")
        startActivity(Intent(this, Home::class.java))
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}