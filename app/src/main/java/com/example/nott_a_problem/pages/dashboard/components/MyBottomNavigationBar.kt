import android.content.Intent
import android.provider.MediaStore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(navController: NavController, items: List<BottomNavItem>) {
    val context = LocalContext.current // Access the context to start an Intent

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 4.dp
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = false, // Update with logic to reflect the selected state
                onClick = {
                    if (item.route == "camera") {
                        // Open the camera directly
                        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        context.startActivity(cameraIntent)
                    } else {
                        // Handle other items (navigate using NavController)
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.label
                    )
                },
                label = { Text(text = item.label, color = Color.White) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    val icon: Int,
    val label: String
)
