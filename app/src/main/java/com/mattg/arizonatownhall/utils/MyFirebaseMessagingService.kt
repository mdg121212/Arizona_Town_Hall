package com.mattg.arizonatownhall.utils

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mattg.arizonatownhall.R
import com.mattg.arizonatownhall.activities.MainActivity


private const val TAG = "fixingmessage"

class MyFirebaseMessagingService : FirebaseMessagingService() {
    val context = Application().baseContext
    var link: String = ""

    //app in foreground
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        //first thing to do is handle fcm messages
        Log.d(TAG, "From: ${remoteMessage.from}")
        //check for data
        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            link = remoteMessage.data["url"].toString()
            Log.i(TAG, "about to send message with $link as data")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sendNotification(remoteMessage.notification?.body.toString(), link)
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                val nm = NotificationManagerCompat.from(this)
                nm.sendNotification(remoteMessage.notification?.body.toString(), this)
            }

        }

        //check for notification payload
        remoteMessage.notification?.let {
            Log.d(TAG, "Message notification body: ${it.body}")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sendNotification(it.body.toString(), link)
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                val nm = NotificationManagerCompat.from(this)
                nm.sendNotification(it.body.toString(), context)
            }
        }


    }

    //showing the notification
    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification(messageBody: String, link: String) {
        Log.i(TAG, "SENDING NOTIFICATION ")
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("url", link ?: null)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_baseline_stars_24)
            .setContentTitle("Arizona Town Hall")
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Channel human readable title",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.notify(channelId, 1, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }


    companion object {
        private const val TAG = "fixingmessage"
    }

}