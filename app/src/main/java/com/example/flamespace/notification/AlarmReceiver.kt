package com.example.flamespace.notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.example.flamespace.R
import com.example.flamespace.user.MainActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        //not sure pa ito
        val notificationBuilder = NotificationCompat.Builder(context, "channel_id")
            .setContentTitle("Notification Title")
            .setContentText("Notification Content")
            .setSmallIcon(R.drawable.notification_icon)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)


        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        notificationBuilder.setSound(defaultSoundUri)
        notificationBuilder.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

        // Set notif LED light
        notificationBuilder.setLights(Color.RED, 3000, 3000)

        notificationManager.notify(0, notificationBuilder.build())
    }
}
