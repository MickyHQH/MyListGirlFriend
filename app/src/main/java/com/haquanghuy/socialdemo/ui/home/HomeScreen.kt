package com.haquanghuy.socialdemo.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.haquanghuy.socialdemo.domain.User
import com.haquanghuy.socialdemo.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onGoToUserDetail: (Int) -> Unit = {},
    onBack: () -> Unit = {},
) {
    var showCreateDialog by remember { mutableStateOf(false) }
    val users by homeViewModel.users.collectAsState()

    if (showCreateDialog) {
        CreateNewDialog(
            onDismissRequest = { showCreateDialog = false },
            onConfirmation = {
                showCreateDialog = false
            }
        )
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
                    Text("Social Demo")
                },
                actions = {
                    ElevatedButton(onClick = { showCreateDialog = true }) {
                        Text("Add new")
                    }
                }
            )
        }
    ) { pd ->
        Column(modifier = Modifier
            .padding(pd)
            .padding(horizontal = 10.dp)) {
            Text("Your girl friend")
            LazyColumn {
                items(users) {
                    UserItem(
                        user = it,
                        onTap = { onGoToUserDetail(it.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun CreateNewDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    var thumbnailUrlValue by remember { mutableStateOf("") }
    var displayNameValue by remember { mutableStateOf("") }
    var bioValue by remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(dismissOnClickOutside = false, usePlatformDefaultWidth = false),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()).padding(12.dp),
            ) {
                Spacer(Modifier.height(20.dp))
                Text(modifier = Modifier.fillMaxWidth(), text = "Create New Girl Friend", style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
//                    Image(
//                        painter = painter,
//                        contentDescription = imageDescription,
//                        contentScale = ContentScale.Fit,
//                        modifier = Modifier
//                            .height(160.dp)
//                    )
                Spacer(Modifier.height(20.dp))
                TextField(
                    value = thumbnailUrlValue,
                    onValueChange = { thumbnailUrlValue = it },
                    label = { Text("Thumbnail URL") },
                    maxLines = 1,
                    modifier = Modifier.padding(20.dp)
                )
                Spacer(Modifier.height(10.dp))
                TextField(
                    value = displayNameValue,
                    onValueChange = { displayNameValue = it },
                    label = { Text("Display name") },
                    maxLines = 1,
                    modifier = Modifier.padding(20.dp)
                )
                Spacer(Modifier.height(10.dp))
                TextField(
                    value = bioValue,
                    onValueChange = { bioValue = it },
                    label = { Text("Bio") },
                    maxLines = 5,
                    modifier = Modifier.padding(20.dp)
                )
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Dismiss")
                    }
                    ElevatedButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    onTap: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTap() }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.thumbnailUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Spacer(Modifier.width(10.dp))
        Text(modifier = Modifier.weight(1f), text = user.displayName)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MyApplicationTheme {
        UserItem(
            User(0, "https://i.mydramalist.com/rPL27_5c.jpg", "Huy Ha 1", "Hello there")
        )
    }
}
