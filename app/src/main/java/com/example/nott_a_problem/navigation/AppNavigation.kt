package com.example.nott_a_problem.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nott_a_problem.pages.ChangePasswordScreen
import com.example.nott_a_problem.pages.SubmittedScreen
import com.example.nott_a_problem.pages.authentication.screens.GetStartedScreen
import com.example.nott_a_problem.pages.authentication.screens.LoginScreen
import com.example.nott_a_problem.pages.authentication.screens.RegistrationScreen
import com.example.nott_a_problem.pages.classifications.ErrorIdentificationScreenIndoor
import com.example.nott_a_problem.pages.classifications.ErrorIdentificationScreenOutdoor
import com.example.nott_a_problem.pages.dashboard.CameraScreen
import com.example.nott_a_problem.pages.dashboard.HomeScreen
import com.example.nott_a_problem.pages.dashboard.LoadingScreen
import com.example.nott_a_problem.pages.services.location.IndoorCheckScreen
import com.example.nott_a_problem.pages.services.location.OutsideCampusScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
    //authentication
        composable("register") { RegistrationScreen(navController) }
        composable("get_started") { GetStartedScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("change_password") { ChangePasswordScreen(navController) }
        composable("camera_screen") { CameraScreen(navController) }

        composable("home") { HomeScreen(navController) }

        composable("loading_screen/{imageUri}") { backStackEntry ->
            val encodedImageUri = backStackEntry.arguments?.getString("imageUri")
            val imageUri = encodedImageUri?.let { Uri.parse(it) }
            if (imageUri != null) {
                LoadingScreen(navController = navController, capturedImageUriString = imageUri)
            }
        }




//        composable("change_profile_picture") { ChangeProfilePictureScreen(navController) }
        composable("outside_campus_screen") { OutsideCampusScreen(navController) }
        composable("indoorCheckScreen/{capturedImageUriString}/{area}/{className}/{subclassName}") { backStackEntry ->
            val capturedImageUriString = Uri.decode(backStackEntry.arguments?.getString("capturedImageUriString"))
            val area = Uri.decode(backStackEntry.arguments?.getString("area"))
            val className = Uri.decode(backStackEntry.arguments?.getString("className"))
            val subclassName = Uri.decode(backStackEntry.arguments?.getString("subclassName"))

            IndoorCheckScreen(
                navController = navController,
                capturedImageUriString = capturedImageUriString, // Replace with your photo file path
                area = area,
                className = className,
                subclassName = subclassName
            )
        }
        composable("error_identification_indoor/{capturedImageUriString}/{area}/{roomNumber}/{className}/{subclassName}") { backStackEntry ->
            val capturedImageUriString = Uri.decode(backStackEntry.arguments?.getString("capturedImageUriString"))
            val area = Uri.decode(backStackEntry.arguments?.getString("area"))
            val roomNumber = Uri.decode(backStackEntry.arguments?.getString("roomNumber"))
            val className = Uri.decode(backStackEntry.arguments?.getString("className"))
            val subclassName = Uri.decode(backStackEntry.arguments?.getString("subclassName"))

            ErrorIdentificationScreenIndoor(
                navController = navController,
                capturedImageUriString = capturedImageUriString, // Replace with the actual path to the photo
                area = area,  // Pass appropriate data here
                roomNumber = roomNumber,
                className = className,
                subclassName = subclassName,
                onNoEventDetected = { /* Handle No Event */ },
                onProblemSubmitted = { navController.navigate("submitted") },
                onNavigateToClassError = { /* Navigate to class error screen */ },
                onNavigateToSubClassError = { /* Navigate to subclass error screen */ }
            )
        }
        composable("error_identification_outdoor/{capturedImageUriString}/{area}/{className}/{subclassName}") { backStackEntry ->
            val capturedImageUriString = Uri.decode(backStackEntry.arguments?.getString("capturedImageUriString"))
            val area = Uri.decode(backStackEntry.arguments?.getString("area"))
            val className = Uri.decode(backStackEntry.arguments?.getString("className"))
            val subclassName = Uri.decode(backStackEntry.arguments?.getString("subclassName"))

            ErrorIdentificationScreenOutdoor(
                navController = navController,
                capturedImageUriString = capturedImageUriString, // Replace with the actual path to the photo
                area = area,  // Pass appropriate data here
                className = className,
                subclassName = subclassName,
                onNoEventDetected = { /* Handle No Event */ },
                onProblemSubmitted = { navController.navigate("submitted") },
                onNavigateToClassError = { /* Navigate to class error screen */ },
                onNavigateToSubClassError = { /* Navigate to subclass error screen */ }
            )
        }


        composable("submitted") { SubmittedScreen(navController) }
    }
}
