package com.haquanghuy.socialdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.net.toUri
import com.haquanghuy.socialdemo.ui.AppScreen
import com.haquanghuy.socialdemo.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
        setContent {
            MyApplicationTheme {
                AppScreen()
            }
        }
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        intent?.extras?.getString("deeplink")?.let {
            val websiteIntent = Intent(Intent.ACTION_VIEW, "myapp://haquanghuy.com/${it}".toUri())
            startActivity(websiteIntent)
        }
    }
}
