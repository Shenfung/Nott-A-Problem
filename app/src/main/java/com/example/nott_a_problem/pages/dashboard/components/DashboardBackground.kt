package com.example.nott_a_problem.pages.dashboard.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nott_a_problem.R

@Composable
fun DashboardBackground() {
    val screenWidth = LocalDensity.current.run { androidx.compose.ui.platform.LocalContext.current.resources.displayMetrics.widthPixels.toDp() }

    val customFontFamily = FontFamily(
        Font(R.font.lobster_regular, FontWeight.Normal) // Replace with your font file's name
    )

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
//        Text(
//            "Nott-A-Problem",
//            fontFamily = customFontFamily,
//            fontWeight = FontWeight.Bold,
//            fontSize = (screenWidth.value / 8).sp,
//            maxLines = 1, // Prevents the text from wrapping to multiple lines
//            overflow = TextOverflow.Ellipsis, // Adds ellipsis when text overflows
//            modifier = Modifier
//                .align(Alignment.TopCenter) // Aligns the text to the top center
//                .padding(top = 16.dp) // Add padding from the top
//                .fillMaxWidth() // Make the text take up the full width
//                .wrapContentWidth(Alignment.CenterHorizontally) // Ensures the text is centered horizontally
//        )
    }
}
