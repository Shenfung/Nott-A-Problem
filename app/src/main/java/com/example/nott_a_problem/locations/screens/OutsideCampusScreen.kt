package com.example.nott_a_problem.locations.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nott_a_problem.R

@Composable
fun OutsideCampusScreen(navController: NavController) {
    val screenWidth = LocalDensity.current.run { androidx.compose.ui.platform.LocalContext.current.resources.displayMetrics.widthPixels.toDp() }
    val customFontFamily = FontFamily(Font(R.font.lobster_regular, FontWeight.Normal))

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
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
                Text(
                    text = "Problem detected is not within the campus! Thank you for your time! Only problems within the campus can be reported.",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )

                Button(
                    onClick = {
                        navController.navigate("home") {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 25.dp, vertical = 16.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBBDEFB)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Back to Homepage",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    )
}
