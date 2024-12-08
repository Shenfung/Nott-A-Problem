package com.example.nott_a_problem.View.Components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun ApplicationBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background gradient
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFB3E5FC),
                            Color(0xFFC779D0),
                        )
                    )
                )
        ) {
            // Top-right radial gradient blob
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF4BC0C8).copy(alpha = 0.7f),
                        Color(0xFFC779D0).copy(alpha = 0.4f)
                    ),
                    center = Offset(size.width, 0f),
                    radius = size.width * 0.5f
                ),
                center = Offset(size.width * 0.8f, size.height * 0.2f),
                radius = size.width * 0.4f
            )
        }

        // Bottom-left radial gradient blob
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFEAC5E).copy(alpha = 0.6f),
                        Color(0xFF4BC0C8).copy(alpha = 0.3f)
                    ),
                    center = Offset(0f, size.height),
                    radius = size.width * 0.6f
                ),
                center = Offset(size.width * 0.2f, size.height * 0.8f),
                radius = size.width * 0.5f
            )
        }
    }
}
