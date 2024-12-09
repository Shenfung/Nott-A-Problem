package com.example.nott_a_problem.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nott_a_problem.repository.ApiService
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ImageClassificationViewModel : ViewModel() {

    private val api = ApiService.api

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
                classifyImage(
                    imageFile = imageFile,
                    onSuccess = { classResult, subclassResult ->
                        className.value = classResult
                        subclassName.value = subclassResult
                        reportImageFile.value = imageFile
                        onSuccess()
                    },
                    onError = { exception ->
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

    private suspend fun classifyImage(
        imageFile: File,
        onSuccess: (String, String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        try {
            val imagePart = convertFiletoMultipartBody(imageFile)

            val classResponse = api.getClassByFile(image = imagePart!!)
            val classResult = classResponse.predictedClass
                ?: throw Exception("Class prediction failed: ${classResponse.error}")

            Log.d("classResult", classResult)

            val subclassResponse = api.getSubclassByFile(image = imagePart, className = classResult.toRequestBody())
            val subclassResult = subclassResponse.predictedClass
                ?: throw Exception("Subclass prediction failed: ${subclassResponse.error}")
            Log.d("subclassResult", subclassResult)

            onSuccess(classResult, subclassResult)
        } catch (e: Exception) {
            Log.e("ClassifyImageError", "Error: ${e.message}")
            onError(e)
        }
    }

    private fun convertFiletoMultipartBody(imageItem: File?): MultipartBody.Part? {
            if (imageItem != null) {
                return MultipartBody.Part.createFormData(
                    "file",
                    imageItem.name,
                    imageItem.asRequestBody("image/*".toMediaTypeOrNull())
                )
            }
        return null
    }
}
