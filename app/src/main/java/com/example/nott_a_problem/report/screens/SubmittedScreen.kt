package com.example.nott_a_problem.report.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.*
import com.example.nott_a_problem.R

@Composable
fun SubmittedScreen(navController: NavHostController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("tick.json"))
    val progress by animateLottieCompositionAsState(composition)
    val customFontFamily = FontFamily(
        Font(R.font.lobster_regular, FontWeight.Normal)
    )
    val screenWidth = LocalDensity.current.run { androidx.compose.ui.platform.LocalContext.current.resources.displayMetrics.widthPixels.toDp() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize()
        ) {
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

            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.size(400.dp)
            )

            Text(
                text = "Thank you for submitting your complaint!",
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Button(
                onClick = {
                    navController.navigate("home") // Navigate to Home
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(width = 200.dp, height = 56.dp)
            ) {
                Text(text = "Back to Home", color = Color.White)
            }
        }
    }
}
