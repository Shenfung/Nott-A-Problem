package com.example.nott_a_problem.authentication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nott_a_problem.R
import com.example.nott_a_problem.authentication.LoginBackground

@Composable
fun GetStartedScreen(navController: NavController) {
    LoginBackground()
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Welcome to Nott-A-Problem!",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "App Illustration",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Text(
            text = "Don't skip out on the faulty facilities. Fix them by just a snap of a picture with Nott-A-Problem. Make our campus a better place.",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Reporting problems increases points which shows that you care about our campus facilities and earn chances to obtain rewards!",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                navController.navigate("home") {
                    popUpTo("getStarted") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Get Started",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}
