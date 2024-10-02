package com.haquanghuy.socialdemo.ui.onboard

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun OnboardScreen(
    onGotoHome: () -> Unit = {}
) {
    val context = LocalContext.current
    val postNotifyPermissionState = rememberPermissionState(
        Manifest.permission.POST_NOTIFICATIONS
    ) {
        if (it) {
            onGotoHome()
        } else {
            Toast.makeText(context, "The post notification is important for this app. Please grant the permission.", Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Social Demo")
                }
            )
        }
    ) { pd ->
        Box(
            modifier = Modifier
                .padding(pd)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ElevatedButton(onClick = {
                if (postNotifyPermissionState.status.isGranted) {
                    onGotoHome()
                } else if (postNotifyPermissionState.status.shouldShowRationale) {
                    Toast.makeText(context, "The post notification is important for this app. Please grant the permission.", Toast.LENGTH_LONG).show()
                } else {
                    postNotifyPermissionState.launchPermissionRequest()
                }
            }) { Text("Discover now") }
        }
    }
}