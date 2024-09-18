package com.example.lab5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab5.ui.theme.Lab5Theme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab5Theme {
                val navController = rememberNavController()
                EventNavigation(navController, "Luis Padilla")
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
    Lab5Theme {
        Greeting("Android")
    }
}

@Composable
fun FavoritesScreen(navController: NavHostController) {
    val viewModel: EventViewModel = viewModel()
    val favoriteEvents = viewModel.events.filter { it.favorite }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Botón de "Back"
        Button(onClick = { navController.navigate("eventList") }) {
            Text("Back")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Your favorites")
        Spacer(modifier = Modifier.height(8.dp))

        if (favoriteEvents.isEmpty()) {
            Text(text = "No favorites found.")
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(favoriteEvents) { event ->
                    FavoriteItem(event) {
                        navController.navigate("eventDetail/${event.id}")
                    }
                }
            }
        }
    }
}




@Composable
fun FavoriteItem(event: Event, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = event.name)
            Text(text = event.location)
        }
    }
}






@Composable
fun FavoriteItem() {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Title")
            Text(text = "Supporting text")
        }
    }
}
data class Event(
    val id: String,
    val name: String,
    val location: String,
    var favorite: Boolean = false
)


@Composable
fun EventListScreen(navController: NavHostController) {
    val viewModel: EventViewModel = viewModel()

    Column {
        LazyColumn {
            items(viewModel.events) { event ->
                EventItem(event) {
                    viewModel.selectedEvent.value = event
                    navController.navigate("eventDetail/${event.id}")
                }
            }
        }
        Button(onClick = { navController.navigate("profile") }) {
            Text("Go to Profile")
        }
    }
}

@Composable
fun EventItem(event: Event, onEventClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onEventClick), // Hacer clic en toda la tarjeta
        shape = RoundedCornerShape(8.dp) // Esquinas redondeadas
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Aquí podrías reemplazar el contenido de la tarjeta con una imagen o cualquier otro contenido
            Image(
                painter = painterResource(id = R.drawable.ic_profile), // Puedes usar un recurso drawable o una imagen
                contentDescription = "Event icon",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = event.name)
                Text(text = event.location)
            }
            IconButton(onClick = onEventClick) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Navigate to details")
            }
        }
    }
}


@Composable
fun ProfileScreen(name: String, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = name)
        Spacer(modifier = Modifier.height(16.dp))

        ProfileOption(
            icon = Icons.Default.Edit,
            label = "Edit Profile",
            onClick = { /* Acción para editar perfil */ }
        )
        ProfileOption(
            icon = Icons.Default.Lock,
            label = "Reset Password",
            onClick = { /* Acción para resetear contraseña */ }
        )
        ProfileOption(
            icon = Icons.Default.Notifications,
            label = "Notifications",
            onClick = { /* Acción para manejar notificaciones */ }
        )
        ProfileOption(
            icon = Icons.Default.Favorite,
            label = "Favorites",
            onClick = { navController.navigate("favorites") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("eventList") }) {
            Text("Go to Event List")
        }
    }
}


@Composable
fun ProfileOption(icon: ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label)
    }
}

@Composable
fun ProfileOption(icon: ImageVector, label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label)
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
    }
}

@Composable
fun EventDetailScreen(eventId: String?, navController: NavHostController) {
    val viewModel: EventViewModel = viewModel()
    val event = viewModel.events.find { it.id == eventId }

    event?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Location: ${event.location}")
            Text(text = "Title: ${event.name}")
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    viewModel.toggleFavorite(event)
                }) {
                    Text(text = if (event.favorite) "Unfavorite" else "Favorite")
                }
                Button(onClick = { /* Acción de compra */ }) {
                    Text(text = "Buy")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("eventList") }) {
                Text(text = "Back")
            }
        }
    }
}



class EventViewModel : ViewModel() {
    // Lista mutable y observable de eventos
    val events = mutableStateListOf(
        Event("1", "Guns And Roses LA", "Los Angeles", favorite = true),
        Event("2", "Guns And Roses Denver", "Denver"),
        Event("3", "Guns And Roses New York", "New York")
    )

    var selectedEvent = mutableStateOf<Event?>(null)

    // Método para marcar un evento como favorito
    fun toggleFavorite(event: Event) {
        val index = events.indexOf(event)
        if (index >= 0) {
            events[index] = events[index].copy(favorite = !event.favorite)
        }
    }
}



@Composable
fun EventNavigation(navController: NavHostController, name: String) {
    NavHost(navController = navController, startDestination = "eventList") {
        composable("eventList") {
            EventListScreen(navController)
        }
        composable("eventDetail/{eventId}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            EventDetailScreen(eventId = eventId, navController = navController)
        }
        composable("profile") {
            ProfileScreen(name, navController) // Pasar el NavHostController
        }
        composable("favorites") {
            FavoritesScreen(navController)
        }
    }
}
