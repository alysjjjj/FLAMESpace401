package com.example.flamespace.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.flamespace.databinding.ActivityForgotBinding
import com.example.flamespace.retrofit.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnResetPassword.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            } else {
                // Call the API endpoint to reset the password
                val service = RetrofitHelper.getService()
                service.resetPassword(email).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@ForgotActivity,
                                "Password reset instructions sent to your email",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            // password reset failure
                            Toast.makeText(
                                this@ForgotActivity,
                                "Failed to reset password. Please try again later",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        // network error
                        Toast.makeText(
                            this@ForgotActivity,
                            "Network error. Please try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }
    }
}
