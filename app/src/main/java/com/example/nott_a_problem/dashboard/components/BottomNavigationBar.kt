package com.example.nott_a_problem.dashboard.components

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.nott_a_problem.R

@Composable
fun BottomNavigationBar(
    navController: NavController,
) {
    val context = LocalContext.current
    val cameraPermission = Manifest.permission.CAMERA
    val currentRoute = navController.currentDestination?.route


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Permission granted!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    NavigationBar(
        containerColor = androidx.compose.ui.graphics.Color(0x9F673AB7)
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = {
                if (currentRoute == "home") {
                    navController.popBackStack("home", false)
                    navController.navigate("home")
                } else {
                    navController.navigate("home")
                }
            },
            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
            selectedIconColor = androidx.compose.ui.graphics.Color.White,
            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            selectedTextColor = androidx.compose.ui.graphics.Color.White,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.camera), contentDescription = "Camera") },
            label = { Text("Camera") },
            selected = currentRoute == "camera",
            onClick = {
                if (ContextCompat.checkSelfPermission(context, cameraPermission) == PackageManager.PERMISSION_GRANTED) {
                    navController.navigate("camera_screen")
                } else {
                    permissionLauncher.launch(cameraPermission)
                }
            },
            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
            selectedIconColor = androidx.compose.ui.graphics.Color.White,
            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            selectedTextColor = androidx.compose.ui.graphics.Color.White,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        )
        )
    }
}








