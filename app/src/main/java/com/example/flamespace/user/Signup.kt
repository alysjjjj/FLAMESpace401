package com.example.flamespace.user

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flamespace.R
import com.example.flamespace.buildings.Home
import com.example.flamespace.databinding.ActivitySignUpBinding
import com.example.flamespace.retrofit.RetrofitHelper
import com.example.flamespace.retrofit.User
import com.example.flamespace.retrofit.UserRequestBody
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set email filter
        val etSignupEmail = binding.etSignupEmail
        setEmailFilter(etSignupEmail)

        binding.btnSignup.setOnClickListener {
            registerUser()
        }

        binding.tvHaveAccount.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
            finish()
        }
    }

    private fun registerUser() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etSignupEmail.text.toString().trim()
        val password = binding.etSignupPassword.text.toString().trim()
        val confirmPassword = binding.etSignupRepassword.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            binding.etSignupPassword.text?.clear()
            binding.etSignupRepassword.text?.clear()
            return
        }

        val userRequestBody = UserRequestBody(name = name, email = email, password = password)
        val service = RetrofitHelper.getService()
        val call = service.signUp(userRequestBody)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    // Save user to Firebase after successful backend registration
                    saveUserToFirebase(name, email)

                    Toast.makeText(this@Signup, "Registered successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@Signup, Home::class.java))  // or any verification activity
                    finish()
                } else {
                    Toast.makeText(this@Signup, "Registration failed", Toast.LENGTH_SHORT).show()
                    Log.e("Signup", "Registration failed: ${response.code()}, ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@Signup, "User Already Exists!", Toast.LENGTH_SHORT).show()
                Log.e("Signup", "Network error: ${t.message}", t)
            }
        })
    }

    private fun saveUserToFirebase(name: String, email: String) {
        val database = FirebaseDatabase.getInstance("https://your-database-url.firebaseio.com/")
        val usersRef = database.getReference("users")
        val userId = usersRef.push().key

        if (userId != null) {
            val userMap = mapOf(
                "id" to userId,
                "name" to name,
                "email" to email
            )

            usersRef.child(userId).setValue(userMap)
                .addOnSuccessListener {
                    Log.d("Firebase", "User saved successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("Firebase", "Error saving user: ${e.message}")
                }
        }
    }

    // Function to set email filter
    private fun setEmailFilter(editText: EditText) {
        val filter = object : InputFilter {
            override fun filter(
                source: CharSequence?,
                start: Int,
                end: Int,
                dest: Spanned?,
                dstart: Int,
                dend: Int
            ): CharSequence? {
                return null
            }
        }
        editText.filters = arrayOf(filter)

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val text = editText.text.toString()
                val emailPattern = "[a-zA-Z0-9._-]+@phinmaed\\.com"
                if (!text.matches(emailPattern.toRegex())) {
                    editText.error = "Invalid email address"
                }
            }
        }
    }
}
