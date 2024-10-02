package com.haquanghuy.socialdemo.ui.userdetail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    userDetailViewModel: UserDetailViewModel = hiltViewModel(),
    userId: Int?,
    onBack: () -> Unit = {},
) {
    val user by userDetailViewModel.user.collectAsState()

    LaunchedEffect(Unit) {
        userDetailViewModel.getUserById(userId)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text("User detail")
                }
            )
        }
    ) { pd ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pd),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            user?.let { u ->
                AsyncImage(
                    model = u.thumbnailUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Spacer(Modifier.height(10.dp))
                Text(u.displayName, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(25.dp))
                Text("Intro:")
                Text(u.displayName, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}