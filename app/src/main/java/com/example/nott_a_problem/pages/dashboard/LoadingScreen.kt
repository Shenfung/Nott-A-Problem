package com.example.nott_a_problem.pages.dashboard

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.nott_a_problem.pages.services.image_classification.ImageClassificationViewModel
import com.example.nott_a_problem.pages.services.location.LocationService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

@Composable
fun LoadingScreen(
    capturedImageUriString: Uri,  // Receive the photo file path as an argument
    navController: NavController
) {
    val context = LocalContext.current


    // Start loading indication (e.g., progress bar)
    LaunchedEffect(Unit) {
        val capturedImagePhotoFile = convertUriToFile(context, capturedImageUriString)
                getUserLocation(context, { area ->
                    Log.d("area", area!!)
                    if (area != null) {
                        val viewModel = ImageClassificationViewModel()
                        viewModel.classifyImageFromFile(
                            imageFile = capturedImagePhotoFile!!,
                            onSuccess = {
                                val className = viewModel.className.value
                                val subclassName = viewModel.subclassName.value

                                if (className != null && subclassName != null) {
                                    navController.navigate(
                                        "indoorCheckScreen/" +
                                                "${Uri.encode(capturedImageUriString.toString())}/" +
                                                "${Uri.encode(area)}/" +
                                                "${Uri.encode(className)}/" +
                                                "${Uri.encode(subclassName)}"
                                    )
                                } else {
                                    Toast.makeText(context, "Classification failed", Toast.LENGTH_SHORT).show()
                                }
                            },
                            onError = {
                                Toast.makeText(context, "Error during classification", Toast.LENGTH_SHORT).show()
                            }
                        )
                    } else {
                        Toast.makeText(context, "Location unavailable", Toast.LENGTH_SHORT).show()
                    }
                }, navController)
    }


    // UI with centered progress indicator and text
    Box(
        contentAlignment = androidx.compose.ui.Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
            androidx.compose.material3.Text(
                text = "Please wait while the AI works its magic...",
                fontSize = 16.sp // Use sp for font size
            )
        }
    }
}

fun getUserLocation(
    context: Context,
    onLocationChecked: (String?) -> Unit,
    navController: NavController
) {
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    val locationService = LocationService()

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val area = locationService.isInsideArea(location.latitude, location.longitude)
                if(area == "outside"){
                    navController.navigate("outside_campus_screen")
                }else{
                    onLocationChecked(area)
                }
            } else {
                onLocationChecked(null) // Location unavailable
            }
        }
    } else {
        onLocationChecked(null) // Permission denied
    }
}


private fun convertUriToFile(context: Context, uri: Uri?): File? {
    val contentResolver = context.contentResolver
    val tempFile = File(
        context.cacheDir,
        "temp_${System.currentTimeMillis()}.jpg"
    )
    tempFile.createNewFile()

    try {
        val inputStream: InputStream? = contentResolver.openInputStream(uri!!)
        val outputStream: OutputStream = FileOutputStream(tempFile)

        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()

    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
    return tempFile
}








