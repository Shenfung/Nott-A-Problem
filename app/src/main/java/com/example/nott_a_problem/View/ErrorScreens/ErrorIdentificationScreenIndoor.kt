package com.example.nott_a_problem.View.ErrorScreens

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.nott_a_problem.R
import com.example.nott_a_problem.Model.uploadReportToFirestoreIndoor
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ErrorIdentificationScreenIndoor(
    navController: NavController,
    capturedImageUriString: String,
    area: String,
    roomNumber: String,
    className: String,
    subclassName:String,
) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val userID = auth.currentUser?.uid
    var showDialog by remember { mutableStateOf(false) }
    val screenWidth = LocalDensity.current.run { androidx.compose.ui.platform.LocalContext.current.resources.displayMetrics.widthPixels.toDp() }
    val customFontFamily = FontFamily(
        Font(R.font.lobster_regular, FontWeight.Normal) // Replace with your font file's name
    )


    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
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
                        text = "Error Identified",
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
                    Text(text = "Room: $roomNumber", style = MaterialTheme.typography.body1)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Is this correct?",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    if (className.lowercase() == "no event") {
                        navController.navigate("no_event_detected")
                    } else {
                        uploadReportToFirestoreIndoor(
                            capturedImageUriString,
                            area,
                            roomNumber,
                            className,
                            subclassName,
                            userID,
                            onSuccess = {
                                navController.navigate("submitted")
                            },
                            onFailure = { error ->
                                Toast.makeText(context, "Could not upload to Firestore", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }) {
                    Text(text = "Yes")
                }
                Button(onClick = { showDialog = true }) {
                    Text(text = "No")
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("What did we get wrong?") },
                    text = {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = {
                                    showDialog = false
                                    navController.navigate(
                                        "class_description_screen_indoor/" +
                                                "${Uri.encode(capturedImageUriString)}/" +
                                                "${Uri.encode(area)}/" +
                                                "${Uri.encode(roomNumber)}/" +
                                                "${Uri.encode(userID)}"
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Class")
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    showDialog = false
                                    navController.navigate("subclass_description_screen_indoor/" +
                                            "${Uri.encode(capturedImageUriString)}/" +
                                            "${Uri.encode(area)}/${Uri.encode(roomNumber)}/" +
                                            "${Uri.encode(className)}/" +
                                            "${Uri.encode(userID)}"
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
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