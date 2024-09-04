package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.layout.ContentScale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FavoritesScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "TodoEvento",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Your favorites",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            fontSize = 20.sp
        )

        val items = listOf(
            "Event 1" to "Supporting text for Event 1",
            "Event 2" to "Supporting text for Event 2",
            "Event 3" to "Supporting text for Event 3",
            "Event 4" to "Supporting text for Event 4"
        )

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(items.chunked(2)) { rowItems ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    rowItems.forEach { (title, supportingText) ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = title, fontSize = 18.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = supportingText)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationsScreen() {
    val notifications = listOf(
        NotificationData("https://example.com/image1.jpg", "New event added", "Don't miss the latest event in your area."),
        NotificationData("https://example.com/image2.jpg", "Event reminder", "Your favorite event starts in 2 hours."),
        NotificationData("https://example.com/image3.jpg", "Event update", "The event location has been changed."),
        NotificationData("https://example.com/image4.jpg", "Event canceled", "Unfortunately, the event has been canceled.")
    )

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(notifications) { notification ->
            NotificationItem(notification)
        }
    }
}

@Composable
fun NotificationItem(notification: NotificationData) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)) {

        Image(
            painter = rememberImagePainter(notification.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .padding(end = 16.dp),
            contentScale = ContentScale.Crop
        )

        Column {
            Text(
                text = notification.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = notification.description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

data class NotificationData(
    val imageUrl: String,
    val title: String,
    val description: String
)

@Composable
fun EventDetailScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = rememberImagePainter("https://example.com/event-image.jpg"),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Event Title",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Date: September 15, 2024",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Text(
            text = "Time: 6:00 PM",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "This is a detailed description of the event. It provides information about what to expect and any other relevant details.",
            fontSize = 16.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /* Acción de favorito */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Favorite")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { /* Acción de comprar */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Buy")
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    val notificationsEnabled = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter("https://example.com/profile-image.jpg"),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Edit Profile",
            fontSize = 18.sp,
            color = Color.Blue
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Reset Password",
            fontSize = 18.sp,
            color = Color.Blue
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Notifications",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
            Switch(
                checked = notificationsEnabled.value,
                onCheckedChange = { notificationsEnabled.value = it }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Favorites",
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}