package com.patrick0422.prosleeper.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.patrick0422.prosleeper.R
import com.patrick0422.prosleeper.util.Constants.CHANNEL_ID
import com.patrick0422.prosleeper.util.Constants.CHANNEL_NAME


class ProSleeperFcmService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notificationManager = NotificationManagerCompat.from(applicationContext)

        val builder: NotificationCompat.Builder = if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
            NotificationCompat.Builder(applicationContext, CHANNEL_ID)
        } else {
            NotificationCompat.Builder(applicationContext, CHANNEL_ID)
        }

        val title: String = message.notification?.title ?: "Title"
        val body: String = message.notification?.body ?: "Body"

        builder.setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_sunny)

        val notification: Notification = builder.build()
        notificationManager.notify(1, notification)
    }
}