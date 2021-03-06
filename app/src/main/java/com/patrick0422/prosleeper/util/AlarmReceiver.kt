package com.patrick0422.prosleeper.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.patrick0422.prosleeper.R
import com.patrick0422.prosleeper.util.Constants.CHANNEL_ID

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        createNotificationChannel(context)
        notifyNotification(context)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "기상 알람",
                NotificationManager.IMPORTANCE_HIGH
            )

            NotificationManagerCompat.from(context)
                .createNotificationChannel(notificationChannel)
        }
    }

    private fun notifyNotification(context: Context) {
        with(NotificationManagerCompat.from(context)) {
            val build = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("알람")
                .setContentText("일어날 시간입니다.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_night)

            notify(0, build.build())
        }
    }
}