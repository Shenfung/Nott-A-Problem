package com.example.nott_a_problem.Model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ImageClassificationViewModel : ViewModel() {

    // API service reference
    private val api = ApiService.api

    // State variables
    val className = mutableStateOf<String?>(null)
    val subclassName = mutableStateOf<String?>(null)
    val errorMessage = mutableStateOf<String?>(null)
    val reportImageFile = mutableStateOf<File?>(null)


    fun classifyImageFromFile(
        imageFile: File,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Call suspend function to classify the image
                classifyImage(
                    imageFile = imageFile,
                    onSuccess = { classResult, subclassResult ->
                        // Update the state on success
                        className.value = classResult
                        subclassName.value = subclassResult
                        reportImageFile.value = imageFile
                        onSuccess()
                    },
                    onError = { exception ->
                        // Update the state on error
                        errorMessage.value = exception.message
                        onError()
                    }
                )
            } catch (e: Exception) {
                errorMessage.value = e.message
                onError()
            }
        }
    }

//    fun classifyImageClassWrong(
//        imageFile: File,
//        onSuccess: () -> Unit,
//        onError: () -> Unit
//    ) {
//        viewModelScope.launch {
//            try {
//                // Step 1: Re-classify the image for the class only
//                val imagePart = convertFiletoMultipartBody(imageFile)
//                val classResponse = api.getClassByFile(image = imagePart!!)
//                val classResult = classResponse.predictedClass
//                    ?: throw Exception("Class prediction failed: ${classResponse.error}")
//
//                // Step 2: Use the previously identified subclass (does not change)
//                val subclassResult = subclassName.value ?: throw Exception("Subclass information missing.")
//
//                // Step 3: Update the state with new class and existing subclass
//                className.value = classResult
//                subclassName.value = subclassResult
//                reportImageFile.value = imageFile
//                onSuccess()
//
//            } catch (e: Exception) {
//                errorMessage.value = e.message
//                onError()
//            }
//        }
//    }
//
//    fun classifyImageSubclassWrong(
//        imageFile: File,
//        onSuccess: () -> Unit,
//        onError: () -> Unit
//    ) {
//        viewModelScope.launch {
//            try {
//                // Step 1: Get the previously identified class (does not change)
//                val classResult = className.value ?: throw Exception("Class information missing.")
//
//                // Step 2: Re-classify the image for the subclass only
//                val imagePart = convertFiletoMultipartBody(imageFile)
//                val subclassResponse = api.getSubclassByFile(image = imagePart!!, className = classResult)
//                val subclassResult = subclassResponse.predictedClass
//                    ?: throw Exception("Subclass prediction failed: ${subclassResponse.error}")
//
//                // Step 3: Update the state with existing class and new subclass
//                className.value = classResult
//                subclassName.value = subclassResult
//                reportImageFile.value = imageFile
//                onSuccess()
//
//            } catch (e: Exception) {
//                errorMessage.value = e.message
//                onError()
//            }
//        }
//    }
//
//    // Function when both class and subclass are identified as wrong (re-classify both)
//    fun classifyImageBothWrong(
//        imageFile: File,
//        onSuccess: () -> Unit,
//        onError: () -> Unit
//    ) {
//        viewModelScope.launch {
//            try {
//                // Step 1: Re-classify the image for class
//                val imagePart = convertFiletoMultipartBody(imageFile)
//                val classResponse = api.getClassByFile(image = imagePart!!)
//                val classResult = classResponse.predictedClass
//                    ?: throw Exception("Class prediction failed: ${classResponse.error}")
//
//                // Step 2: Re-classify the image for subclass
//                val subclassResponse = api.getSubclassByFile(image = imagePart, className = classResult)
//                val subclassResult = subclassResponse.predictedClass
//                    ?: throw Exception("Subclass prediction failed: ${subclassResponse.error}")
//
//                // Step 3: Update state with new class and new subclass
//                className.value = classResult
//                subclassName.value = subclassResult
//                reportImageFile.value = imageFile
//                onSuccess()
//
//            } catch (e: Exception) {
//                errorMessage.value = e.message
//                onError()
//            }
//        }
//    }

    private suspend fun classifyImage(
        imageFile: File,
        onSuccess: (String, String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        try {
            // Prepare the image for upload
            val imagePart = convertFiletoMultipartBody(imageFile)

            // Get class prediction
            val classResponse = api.getClassByFile(image = imagePart!!)
            val classResult = classResponse.predictedClass
                ?: throw Exception("Class prediction failed: ${classResponse.error}")

            Log.d("classResult", classResult)
            // Get subclass prediction
            val subclassResponse = api.getSubclassByFile(image = imagePart, className = classResult.toRequestBody())
            val subclassResult = subclassResponse.predictedClass
                ?: throw Exception("Subclass prediction failed: ${subclassResponse.error}")
            Log.d("subclassResult", subclassResult)

            // Pass results to the success callback
            onSuccess(classResult, subclassResult)
        } catch (e: Exception) {
            Log.e("ClassifyImageError", "Error: ${e.message}")
            onError(e)
        }
    }


    private fun convertFiletoMultipartBody(imageItem: File?): MultipartBody.Part? {
            if (imageItem != null) {
                return MultipartBody.Part.createFormData(
                    "file", // Singular form since it's a single file
                    imageItem.name,
                    imageItem.asRequestBody("image/*".toMediaTypeOrNull())
                )
            }
        return null
    }
}
