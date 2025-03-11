package com.example.flamespace.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flamespace.buildings.Home
import com.example.flamespace.databinding.ActivitySignInBinding
import com.example.flamespace.retrofit.LoginResponse
import com.example.flamespace.retrofit.LoginUser
import com.example.flamespace.retrofit.RetrofitHelper
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            } else {
                // Call the login API
                loginUser(email, password)
            }
        }

        binding.tvHaventAccount.setOnClickListener {
            startActivity(Intent(this, Home::class.java))
        }

        binding.tvForgotPw.setOnClickListener {
            startActivity(Intent(this, ForgotActivity::class.java))
        }
    }

    private fun loginUser(email: String, password: String) {
        val apiService = RetrofitHelper.getService()
        val call = apiService.loginUser(LoginUser(email = email, password = password))

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                try {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse != null) {
                            val authToken = "Bearer ${loginResponse.token}"
                            val intent = Intent(this@SignIn, Home::class.java)
                            intent.putExtra("authToken", authToken)
                            startActivity(intent)
                            finish()
                            // Add toast for successful login
                            Toast.makeText(this@SignIn, "Login successful", Toast.LENGTH_SHORT).show()
                        } else {
                            // Handle null response body
                            Toast.makeText(this@SignIn, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Handle unsuccessful response
                        val errorBody = response.errorBody()?.string()
                        val errorMessage = JSONObject(errorBody).getString("message")
                        Toast.makeText(this@SignIn, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("SignInActivity", "Error during login: ${e.message}", e)
                    Toast.makeText(this@SignIn, "An error occurred. Please try again", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("SignInActivity", "Network error: ${t.message}", t)
                Toast.makeText(this@SignIn, "Network error. Please try again", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
