package com.example.nott_a_problem.pages.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
//import com.example.nott_a_problem.pages.dashboard.components.MyBottomNavigationBar

@Composable
fun HomeScreen(navController: NavHostController) {
    DashboardBackground()
    Column {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Welcome to the Main Screen!")
        }

    }
//    MyBottomNavigationBar()
}
