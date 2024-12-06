import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.location.Location
import android.net.Uri
import com.example.nott_a_problem.pages.services.location.LocationService

@Composable
fun BottomNavigationBar(
    navController: NavController,
) {
    val context = LocalContext.current
    val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)
    val currentRoute = navController.currentDestination?.route

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsResult ->
        val allGranted = permissionsResult.all { it.value }
        if (allGranted) {
            handleCameraAndLocation(context, navController)
        } else {
            Toast.makeText(
                context,
                "Permissions denied: ${permissionsResult.filter { !it.value }.keys.joinToString()}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }



    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = {
                if (currentRoute == "home") {
                    // Pop the current route and navigate to home again to refresh the screen
                    navController.popBackStack("home", false)
                    navController.navigate("home")
                } else {
                    navController.navigate("home")
                }
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Camera, contentDescription = "Camera") },
            label = { Text("Camera") },
            selected = currentRoute == "camera",
            onClick = {
                if (permissions.all { permission ->
                        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
                    }) {
                    handleCameraAndLocation(context, navController)
                } else {
                    permissionLauncher.launch(permissions)
                }
            }
        )
    }
}

private fun handleCameraAndLocation(context: Context, navController: NavController) {
    getUserLocation(context) { area ->
        if (area != null) {
            launchCamera(context) { photoUri ->
                if (photoUri != null) {
                    Toast.makeText(
                        context,
                        "Photo saved at: $photoUri\nArea: $area",
                        Toast.LENGTH_LONG
                    ).show()
                    // Pass the area to the IndoorCheckScreen
                    navController.navigate("indoorCheckScreen/$area")
                } else {
                    Toast.makeText(context, "Failed to create photo URI", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "Location unavailable", Toast.LENGTH_SHORT).show()
        }
    }
}

fun launchCamera(context: Context, onPhotoUriCreated: (Uri?) -> Unit) {
    val photoUri = createImageUri(context)
    onPhotoUriCreated(photoUri)

    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
        putExtra(MediaStore.EXTRA_OUTPUT, photoUri) // Save image to the specified URI
    }
    if (cameraIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(cameraIntent)
    } else {
        Toast.makeText(context, "No camera app found", Toast.LENGTH_SHORT).show()
    }
}



fun getUserLocation(context: Context, onLocationChecked: (String?) -> Unit) {
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    val locationService = LocationService()

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val area = locationService.isInsideArea(location.latitude, location.longitude)
                onLocationChecked(area)
            } else {
                onLocationChecked(null) // Location unavailable
            }
        }
    } else {
        onLocationChecked(null) // Permission denied
    }
}



fun createImageUri(context: Context): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "photo_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }
    return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}


