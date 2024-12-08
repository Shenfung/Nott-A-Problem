package com.example.nott_a_problem.View.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.nott_a_problem.R

@Composable
fun NoEventDetectedScreenContent(navController: NavController) {
    val screenWidth = LocalDensity.current.run { androidx.compose.ui.platform.LocalContext.current.resources.displayMetrics.widthPixels.toDp() }
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("smile.json"))
    val progress by animateLottieCompositionAsState(composition)

    val customFontFamily = FontFamily(
        Font(R.font.lobster_regular, FontWeight.Normal) // Replace with your font file's name
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the "not-a-problem" text
        Text(
            "Nott-A-Problem",
            fontFamily = customFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (screenWidth.value / 8).sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        // Display the smiley face using JSON or any appropriate resource
        // For this example, using a painter resource
        Spacer(modifier = Modifier.height(16.dp))
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.size(400.dp)
        )

        // Display "no event detected" text
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No event detected! Thank you for your time.",
            style = MaterialTheme.typography.displayMedium
        )

        // Button to go back to home
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Handle back to home action */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Back to Home")
        }
    }
}