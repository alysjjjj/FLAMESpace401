package com.example.flamespace.user

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.flamespace.R
import com.example.flamespace.profile.Current
import com.google.firebase.Firebase
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonClick = findViewById<Button>(R.id.mainLogin)
        buttonClick.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
//            writeMessage()
        }

        val btn = findViewById<Button>(R.id.mainSignup)
        btn.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
//            writeMessage()
        }

        showNotification()
    }

//    private fun writeMessage() {
//        val sdatabase = Firebase.database
//        val myRef = database.getReference("message")
//        myRef.setValue("Hello")
//    }

    private fun showNotification() {
        val notificationManager = NotificationManagerCompat.from(this)
        val intent = Intent(this, Current::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val channelId = getString(R.string.default_notification_channel_id)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("FLAMESpace")
            .setContentText("hello, papalitan to ha donut forgetti")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        val channel = NotificationChannel(channelId, "Channel Name", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY),
                PERMISSION_REQUEST_CODE
            )
            return
        }
        notificationManager.notify(1, notificationBuilder.build())
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
    }

    private val LOGOUT_REQUEST_CODE = 1001

    fun startLogoutActivity() {
        startActivityForResult(Intent(this, Logout::class.java), LOGOUT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOGOUT_REQUEST_CODE && resultCode == RESULT_OK) {
            finishAffinity()
        }
    }
}

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == LOGOUT_REQUEST_CODE && resultCode == RESULT_OK) {
//            // User has logged out
//            finishAffinity() // Finish the activity and all its descendants
//        }
//    }


