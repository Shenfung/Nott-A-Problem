package com.example.nott_a_problem.pages

import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Button
import androidx.compose.material3.Text

@Composable
fun CameraScreen() {
    val context = LocalContext.current

    Button(onClick = {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            context.startActivity(cameraIntent)
        } catch (e: Exception) {
            Toast.makeText(context, "Camera not available", Toast.LENGTH_SHORT).show()
        }
    }) {
        Text(text = "Open Camera")
    }
}
