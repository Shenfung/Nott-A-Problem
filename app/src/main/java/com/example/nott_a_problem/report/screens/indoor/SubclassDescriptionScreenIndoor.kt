package com.example.nott_a_problem.report.screens.indoor

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nott_a_problem.repository.uploadReportToFirestoreIndoor
import com.example.nott_a_problem.R

@Composable
fun SubclassDescriptionScreenIndoor(
    navController: NavController,
    capturedImageUriString: String,
    area: String,
    roomNumber: String,
    className: String,
    userID: String,
) {
    var subclassDescription by remember { mutableStateOf(TextFieldValue("")) }
    val screenWidth = LocalDensity.current.run { LocalContext.current.resources.displayMetrics.widthPixels.toDp() }
    val customFontFamily = FontFamily(
        Font(R.font.lobster_regular, FontWeight.Normal)
    )
    val context = LocalContext.current
    val decodedCapturedImageUriString = Uri.decode(capturedImageUriString)
    val decodedArea = Uri.decode(area)
    val decodedUserID = Uri.decode(userID)
    val focusManager = LocalFocusManager.current

    Scaffold(
        content = {
            PaddingValues(
                top = 16.dp,
                bottom = 16.dp,
                start = 16.dp,
                end = 16.dp
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
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
                Text("Please give a description for the subclass.")
                TextField(
                    value = subclassDescription.text,
                    onValueChange = { subclassDescription = TextFieldValue(it) },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            val updatedSubclass = subclassDescription.text.trim()
                            uploadReportToFirestoreIndoor(
                                decodedCapturedImageUriString,
                                decodedArea,
                                roomNumber,
                                className,
                                updatedSubclass,
                                decodedUserID,
                                onSuccess = {
                                    navController.navigate("submitted")
                                },
                                onFailure = { error ->
                                    Toast.makeText(context, "Could not upload to Firestore", Toast.LENGTH_SHORT).show()
                                }
                            )
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Submit")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    )
}


