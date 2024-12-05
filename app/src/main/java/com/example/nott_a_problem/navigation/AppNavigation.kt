package com.example.nott_a_problem.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nott_a_problem.pages.CameraScreen
import com.example.nott_a_problem.pages.ChangePasswordScreen
import com.example.nott_a_problem.pages.SubmittedScreen
import com.example.nott_a_problem.pages.authentication.screens.GetStartedScreen
import com.example.nott_a_problem.pages.authentication.screens.LoginScreen
import com.example.nott_a_problem.pages.authentication.screens.RegistrationScreen
import com.example.nott_a_problem.pages.dashboard.HomeScreen
import com.example.nott_a_problem.pages.profile.ChangeProfilePictureScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegistrationScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("get_started") { GetStartedScreen(navController) }
        composable("change_password") { ChangePasswordScreen(navController) }
        composable("submitted") { SubmittedScreen(navController) }
        composable("camera") { CameraScreen() }
        composable("change_profile_picture") { ChangeProfilePictureScreen(navController) }
    }
}
