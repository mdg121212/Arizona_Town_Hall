package com.mattg.aztownhall.utils


import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mattg.aztownhall.R

private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

fun NotificationManagerCompat.sendNotification(messageBody: String, applicationContext: Context){
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.default_notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_baseline_stars_24)
        .setContentTitle("FCM MESSAGE")
        .setContentText(messageBody)

        notify(NOTIFICATION_ID, builder.build())

}