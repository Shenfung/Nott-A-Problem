package com.example.nott_a_problem

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nott_a_problem.pages.authentication.GetStartedScreen
import com.example.nott_a_problem.pages.authentication.LoginScreen
import com.example.nott_a_problem.pages.authentication.RegistrationScreen
import com.example.nott_a_problem.pages.dashboard.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "get_started") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegistrationScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("get_started") { GetStartedScreen(navController) }
    }
}
