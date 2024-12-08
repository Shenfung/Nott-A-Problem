package com.example.nott_a_problem.pages.classifications

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.nott_a_problem.R

@Composable
fun ErrorIdentificationScreenOutdoor(
    navController: NavController,
    capturedImageUriString: String,
    area: String,
    className: String,
    subclassName:String,
    onNoEventDetected: () -> Unit,
    onProblemSubmitted: () -> Unit,
    onNavigateToClassError: () -> Unit,
    onNavigateToSubClassError: () -> Unit
) {

    val context = LocalContext.current

    var isErrorReported by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    val screenWidth = LocalDensity.current.run { androidx.compose.ui.platform.LocalContext.current.resources.displayMetrics.widthPixels.toDp() }

    val customFontFamily = FontFamily(
        Font(R.font.lobster_regular, FontWeight.Normal) // Replace with your font file's name
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            androidx.compose.material3.Text(
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

            // Image Display
            Image(
                painter = rememberImagePainter(Uri.parse(capturedImageUriString)),
                contentDescription = "Uploaded Image",
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Card for First Trial
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                elevation = 8.dp,
                shape = RoundedCornerShape(15.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "First Trial",
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (className != null && subclassName != null) {
                        Text(text = "Class: $className", style = MaterialTheme.typography.h6)
                        Text(text = "Subclass: $subclassName", style = MaterialTheme.typography.body1)
                    }
                    Text(text = "Area: $area", style = MaterialTheme.typography.body1)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    if (className.lowercase() == "no event") {
                        onNoEventDetected()
                    } else {
                        onProblemSubmitted()
                    }
                }) {
                    Text(text = "Yes")
                }
                Button(onClick = { showDialog = true }) {
                    Text(text = "No")
                }
            }

            // Show Dialog if needed
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("What did we get wrong?") },
                    text = {
                        Column {
                            Button(onClick = {
                                showDialog = false
                                onNavigateToClassError()
                            }) {
                                Text("Class")
                            }
                            Button(onClick = {
                                showDialog = false
                                onNavigateToSubClassError()
                            }) {
                                Text("Subclass")
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Close")
                        }
                    }
                )
            }
        }
    }
}

