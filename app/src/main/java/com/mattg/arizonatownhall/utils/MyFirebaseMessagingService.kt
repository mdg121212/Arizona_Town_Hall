package com.mattg.arizonatownhall.utils

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mattg.arizonatownhall.activities.MainActivity
import com.mattg.arizonatownhall.R


private const val TAG = "MESSAGINGSERVICE"

class MyFirebaseMessagingService : FirebaseMessagingService() {
    val context = Application().baseContext
    var link: String = ""

    //app in foreground
    override fun onMessageReceived(p0: RemoteMessage) {
        //first thing to do is handle fcm messages
        Log.d(TAG, "From: ${p0.from}")
        //check for data
        p0.data.isNotEmpty().let {
            Log.d(TAG, "Message data payload: ${p0.data}")
            link = p0.data["url"].toString()

        }

        //check for notification payload
        p0.notification?.let {
            Log.d(TAG, "Message notification body: ${it.body}")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sendNotification(it.body.toString(), link)
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                val nm = NotificationManagerCompat.from(this)
            }
        }


    }

    //showing the notification
    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification(messageBody: String, link: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("url", link)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
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

    fun openUrlLinkFromMessage(context: Context, url: String) {
        val url = url
        if (url != "") {
            val builder = CustomTabsIntent.Builder()
            val colorInt = Color.parseColor("#7f0000")
            builder.setToolbarColor(colorInt)
            val customTabIntent = builder.build()
            customTabIntent.launchUrl(context, Uri.parse(url))
        }
    }

}