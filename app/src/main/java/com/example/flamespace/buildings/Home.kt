package com.example.flamespace.buildings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.flamespace.R
import com.example.flamespace.R.id.be
import com.example.flamespace.R.id.cahs
import com.example.flamespace.R.id.cite
import com.example.flamespace.R.id.cma
import com.example.flamespace.R.id.mba
import com.example.flamespace.R.id.nh
import com.example.flamespace.R.id.profile
import com.example.flamespace.profile.Profile

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val authToken = intent.getStringExtra("authToken")
        val userName = intent.getStringExtra("userName")
        val userEmail = intent.getStringExtra("userEmail")

        if (authToken == null || userName == null || userEmail == null) {
            // User is not authenticated, proceed with the Home activity
            // Your button click listeners...
            val button = findViewById<ImageView>(R.id.cite)
            button.setOnClickListener {
                val intent = Intent(this, Ptc::class.java)
                startActivity(intent)
            }

            val btn = findViewById<ImageView>(R.id.profile)
            btn.setOnClickListener {
                val intent = Intent(this, Profile::class.java)
                startActivity(intent)
            }

            val btn2 = findViewById<ImageView>(R.id.cma)
            btn2.setOnClickListener {
                val intent = Intent(this, Cma::class.java)
                startActivity(intent)
            }

            val btn3 = findViewById<ImageView>(R.id.mba)
            btn3.setOnClickListener {
                val intent = Intent(this, Mba::class.java)
                startActivity(intent)
            }

            val btn4 = findViewById<ImageView>(R.id.nh)
            btn4.setOnClickListener {
                val intent = Intent(this, Nh::class.java)
                startActivity(intent)
            }

            val btn5 = findViewById<ImageView>(R.id.cahs)
            btn5.setOnClickListener {
                val intent = Intent(this, Cahs::class.java)
                startActivity(intent)
            }

            val btn6 = findViewById<ImageView>(R.id.be)
            btn6.setOnClickListener {
                val intent = Intent(this, Be::class.java)
                startActivity(intent)
            }
        }
    }

}
