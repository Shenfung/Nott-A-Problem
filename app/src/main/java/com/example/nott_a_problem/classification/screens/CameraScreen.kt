package com.example.nott_a_problem.classification.screens

import android.content.ContentValues
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.nott_a_problem.R
import com.example.nott_a_problem.dashboard.components.ApplicationBackground


@Composable
fun CameraScreen(navController: NavController){
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showImage by remember { mutableStateOf(false) }
    val screenWidth = LocalDensity.current.run { LocalContext.current.resources.displayMetrics.widthPixels.toDp() }
    val customFontFamily = FontFamily(Font(R.font.lobster_regular, FontWeight.Normal))
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            showImage = true
        } else {
            imageUri = null
        }
    }
    val createImageUri = {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "captured_image_${System.currentTimeMillis()}.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }
        context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }

    ApplicationBackground()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
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
        if (showImage && imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUri),
                contentDescription = "Captured Image",
                modifier = Modifier.size(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            imageUri = createImageUri()
            imageUri?.let { uri ->
                takePictureLauncher.launch(uri)
            }


        }) {
            Text(text = "Take Picture")
        }
        Button(onClick = {
            imageUri?.let { uri ->
                navController.navigate("loading_screen/${Uri.encode(uri.toString())}")
            } ?: run {
                Toast.makeText(context, "No image captured. Please take a picture first.", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Get Results")
        }
    }
}

