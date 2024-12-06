package com.example.nott_a_problem.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.example.nott_a_problem.pages.CameraScreen
import com.example.nott_a_problem.pages.ChangePasswordScreen
import com.example.nott_a_problem.pages.SubmittedScreen
import com.example.nott_a_problem.pages.authentication.screens.GetStartedScreen
import com.example.nott_a_problem.pages.authentication.screens.LoginScreen
import com.example.nott_a_problem.pages.authentication.screens.RegistrationScreen
import com.example.nott_a_problem.pages.classifications.ErrorIdentificationScreen
import com.example.nott_a_problem.pages.dashboard.HomeScreen
import com.example.nott_a_problem.pages.profile.ChangeProfilePictureScreen
import com.example.nott_a_problem.pages.services.image_classification.ImageClassificationViewModel
import com.example.nott_a_problem.pages.services.location.IndoorCheckScreen
import com.example.nott_a_problem.pages.services.location.OutsideCampusScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegistrationScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("get_started") { GetStartedScreen(navController) }
        composable("change_password") { ChangePasswordScreen(navController) }
        composable("submitted") { SubmittedScreen(navController) }
        composable("change_profile_picture") { ChangeProfilePictureScreen(navController) }
        composable("outside_campus_screen") { OutsideCampusScreen(navController) }
        composable("indoorCheckScreen/{area}") { backStackEntry ->
            val area = backStackEntry.arguments?.getString("area") ?: "Unknown"
            IndoorCheckScreen(navController, area)
        }
        composable("error_identification/{roomNumber}/{locationInfo}/{firstPredictionResult}") { backStackEntry ->

            val roomNumber = backStackEntry.arguments?.getString("roomNumber") ?: "Unknown"
//            val area = backStackEntry.arguments?.getString("area") ?: "Unknown"
            val locationInfo = backStackEntry.arguments?.getString("area") ?: "Unknown"
            val firstPredictionResult = backStackEntry.arguments?.getString("firstPredictionResult")?.split(",") ?: emptyList()
            val viewModel: ImageClassificationViewModel = viewModel()

            ErrorIdentificationScreen(
                viewModel = viewModel, // Ensure viewModel is provided
                firstPredictionResult = firstPredictionResult,
                locationInfo = locationInfo,
                roomNumber = roomNumber,
                onNoEventDetected = { /* Handle No Event */ },
                onProblemSubmitted = {
                /* Handle Problem Submitted */
                    navController.navigate("submitted")
                },
                onNavigateToClassError = { /* Navigate to class error screen */ },
                onNavigateToSubClassError = { /* Navigate to subclass error screen */ }
            )
        }
    }
}
