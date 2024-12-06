package com.example.nott_a_problem.pages.classifications

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File

@Composable
fun ClassErrorIdentityScreen(
    imageFile: File,
    secondPredictionResult: List<String>,
    locationInfo: String,
    roomNumber: String,
    latitude: Double,
    longitude: Double
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Text(
                text = "Nott-A-Problem",
                fontFamily = FontFamily.Serif,
                fontSize = 50.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )

            // Image display
            Image(
                bitmap = BitmapFactory.decodeFile(imageFile.absolutePath).asImageBitmap(),
                contentDescription = "Problem Image",
                modifier = Modifier
                    .size(300.dp)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Second trial Card
            Card(
                modifier = Modifier.padding(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                shape = RoundedCornerShape(15.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Second Trial",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    if (secondPredictionResult.isNotEmpty()) {
                        Text("Class: ${secondPredictionResult[0].replace('_', ' ')}", fontSize = 20.sp)
                        Text("Subclass: ${secondPredictionResult[1]}", fontSize = 20.sp)
                    } else {
                        Text("The location is $locationInfo", fontSize = 20.sp)
                    }
                }
            }

            // Action buttons
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Button(
                    onClick = { /* Handle Yes action */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Yes")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { /* Handle No action */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("No")
                }
            }
        }
    }
}
