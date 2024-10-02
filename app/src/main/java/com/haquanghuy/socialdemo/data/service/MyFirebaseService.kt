package com.haquanghuy.socialdemo.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.haquanghuy.socialdemo.MainActivity
import com.haquanghuy.socialdemo.R


class MyFirebaseService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification!!.body)

            sendNotification(remoteMessage.data, remoteMessage.notification!!.body)
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

    private fun sendNotification(data: MutableMap<String, String>, messageBody: String?) {
        try {
            val intent = Intent(
                this,
                MainActivity::class.java
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val deepLinkIntent = Intent(
                Intent.ACTION_VIEW,
                "myapp://haquanghuy.com/${data["deeplink"]}".toUri(),
            )

            val pendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
                addNextIntentWithParentStack(deepLinkIntent)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            }

            val channelId: String = getString(R.string.default_notification_channel_id)
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val notificationBuilder: NotificationCompat.Builder =
                NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            resources,
                            R.drawable.ic_launcher_background,
                        )
                    )
                    .setContentTitle(getString(R.string.project_id))
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    "FCM channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager.createNotificationChannel(channel)
            }

            notificationManager.notify(0, notificationBuilder.build())
        } catch (e: Exception) {
            Log.d("MyFirebaseService", "error ${e.message}")
        }
    }

    companion object {
        private const val TAG = "MyFirebaseService"
    }
}

