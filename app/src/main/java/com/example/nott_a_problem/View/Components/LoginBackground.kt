package com.example.nott_a_problem.View.Components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nott_a_problem.R

@Composable
fun LoginBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Top-left gradient blob
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(  // Add gradient background here
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE1BEE7).copy(alpha = 0.8f),
                            Color(0xFFBBDEFB).copy(alpha = 0.8f)
                        )
                    )
                )
        ) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF64B5F6).copy(alpha = 0.7f),
                        Color(0xFF1976D2).copy(alpha = 0.5f)
                    ),
                    center = Offset(0f, 0f),
                    radius = size.width * 0.8f
                ),
                center = Offset(size.width * 0.1f, size.height * 0.1f),
                radius = size.width * 0.6f
            )
        }

        // Bottom-right gradient blob
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFBBDEFB).copy(alpha = 0.7f),
                        Color(0xFFE1BEE7).copy(alpha = 0.5f)
                    ),
                    center = Offset(size.width, size.height),
                    radius = size.width * 0.9f
                ),
                center = Offset(size.width * 0.85f, size.height * 0.85f),
                radius = size.width * 0.5f
            )
        }

        // Additional smaller circles scattered around
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawCircle(
                color = Color(0xFF64B5F6).copy(alpha = 0.6f),
                center = Offset(size.width * 0.3f, size.height * 0.2f),
                radius = 100f
            )

            drawCircle(
                color = Color(0xFF9C27B0).copy(alpha = 0.4f),
                center = Offset(size.width * 0.7f, size.height * 0.3f),
                radius = 120f
            )

            drawCircle(
                color = Color(0xFF1976D2).copy(alpha = 0.5f),
                center = Offset(size.width * 0.15f, size.height * 0.75f),
                radius = 80f
            )

            drawCircle(
                color = Color(0xFFE1BEE7).copy(alpha = 0.5f),
                center = Offset(size.width * 0.9f, size.height * 0.6f),
                radius = 150f
            )
        }
        Image(
            painter = painterResource(id = R.drawable.notts_logo), // Replace with your logo resource
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(150.dp)
        )
    }
}

