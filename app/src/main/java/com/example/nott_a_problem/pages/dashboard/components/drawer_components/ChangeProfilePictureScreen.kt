//package com.example.nott_a_problem.pages.profile
//
//import android.content.Context
//import android.content.res.Resources
//import android.graphics.Bitmap
//import android.net.Uri
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import coil.compose.rememberImagePainter
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.storage.FirebaseStorage
//import java.io.InputStream
//import java.util.*
//
//@Composable
//fun ChangeProfilePictureScreen(navController: NavController) {
//    val context = LocalContext.current
//    val userId = FirebaseAuth.getInstance().currentUser?.uid
//
//    if (userId == null) {
//        Log.e("ChangeProfilePicture", "User ID is null")
//        return // Exit the function early to prevent further errors
//    }
//
//    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
//    var isUploading by remember { mutableStateOf(false) }
//    var uploadMessage by remember { mutableStateOf("") }
//
//    // Firebase references
//    val firestore = FirebaseFirestore.getInstance()
//    val storage = FirebaseStorage.getInstance()
//
//    // Image picker
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//        selectedImageUri = uri
//    }
//
//    val img : Bitmap = Bitmap.Factory.decodeResources(Resources.getSystem(), android.R.drawable.home)
//    val bitmap = remember { mutableStateOf(img) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Change Profile Picture",
//            style = MaterialTheme.typography.titleLarge,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        // Display the selected image or a placeholder
//        if (selectedImageUri != null) {
//            Image(
//                painter = rememberImagePainter(selectedImageUri),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(150.dp)
//                    .clip(CircleShape)
//            )
//        } else {
//            Text("No image selected", style = MaterialTheme.typography.bodyLarge)
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Button to pick an image
//        Button(onClick = { launcher.launch("image/*") }) {
//            Text(text = "Choose Image")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Button to upload the image
//        Button(
//            onClick = {
//                if (selectedImageUri != null && userId != null) {
//                    isUploading = true
//                    uploadImageToFirebaseStorage(
//                        context = context,
//                        userId = userId,
//                        uri = selectedImageUri!!,
//                        storage = storage,
//                        firestore = firestore,
//                        onUploadSuccess = { message ->
//                            uploadMessage = message
//                            isUploading = false
//                        },
//                        onUploadFailure = { error ->
//                            uploadMessage = error
//                            isUploading = false
//                        }
//                    )
//                } else {
//                    uploadMessage = "Please select an image first."
//                }
//            },
//            enabled = !isUploading
//        ) {
//            Text(text = if (isUploading) "Uploading..." else "Upload Image")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Display upload message
//        if (uploadMessage.isNotEmpty()) {
//            Text(text = uploadMessage, style = MaterialTheme.typography.bodyMedium)
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Navigate back button
//        Button(onClick = { navController.popBackStack() }) {
//            Text(text = "Back to Dashboard")
//        }
//    }
//}
//
////fun uploadImageToFirebaseStorage(
////    context: Context,
////    userId: String,
////    uri: Uri,
////    storage: FirebaseStorage,
////    firestore: FirebaseFirestore,
////    onUploadSuccess: (String) -> Unit,
////    onUploadFailure: (String) -> Unit
////) {
////    try {
////        // Read the file as bytes
////        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
////        val imageBytes = inputStream?.readBytes()
////        inputStream?.close()
////
////        if (imageBytes != null) {
////            val storageRef = storage.reference.child("profile_pictures/$userId/${UUID.randomUUID()}.jpg")
////
////            // Use putBytes() for uploading
////            val uploadTask = storageRef.putBytes(imageBytes)
////
////            uploadTask
////                .addOnSuccessListener {
////                    storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
////                        // Save the download URL to Firestore
////                        firestore.collection("users").document(userId)
////                            .update("profilePictureUrl", downloadUri.toString())
////                            .addOnSuccessListener {
////                                onUploadSuccess("Profile picture updated successfully!")
////                            }
////                            .addOnFailureListener { exception ->
////                                Log.e("ChangeProfilePicture", "Firestore update failed: ${exception.message}")
////                                onUploadFailure("Failed to save profile picture URL.")
////                            }
////                    }.addOnFailureListener { exception ->
////                        Log.e("ChangeProfilePicture", "Download URL failed: ${exception.message}")
////                        onUploadFailure("Failed to get download URL.")
////                    }
////                }
////                .addOnFailureListener { exception ->
////                    Log.e("ChangeProfilePicture", "Upload failed: ${exception.message}")
////                    onUploadFailure("Failed to upload image.")
////                }
////        } else {
////            onUploadFailure("Failed to read image bytes.")
////        }
////    } catch (exception: Exception) {
////        Log.e("ChangeProfilePicture", "Error reading image file: ${exception.message}")
////        onUploadFailure("Failed to read image file.")
////    }
////}
//
//fun uploadImageToFirebaseStorage(bitmap: Bitmap, context: ComponentActivity, callback: (Boolean) -> Unit){
//    val storageRef = Firebase.storage.reference
//    val imageRef = storageRef.child(images/${bitmap})
//
//    val baos = ByteArrayOutputStream()
//    bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos)
//    val imageData = baos.toByteArray()
//
//    imageRef.putBytes(imageData).addOnSuccessListener {
//        callback(true)
//    }.addOnFailureListener{
//        callback(false)
//    }
//
//}