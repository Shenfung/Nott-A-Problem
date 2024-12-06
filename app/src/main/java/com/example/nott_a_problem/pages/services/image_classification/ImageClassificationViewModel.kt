package com.example.nott_a_problem.pages.services.image_classification

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ImageClassificationViewModel : ViewModel() {
    private val api = ApiService.api

    val className = mutableStateOf<String?>(null)
    val subclassName = mutableStateOf<String?>(null)
    val errorMessage = mutableStateOf<String?>(null)
    val reportImageUrl = mutableStateOf<String?>(null)

    fun classifyImage(
        imageUrl: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val classResponse = api.getClassByUrl(imageUrl)
                val classResult = classResponse.predictedClass
                    ?: throw Exception("Class prediction failed: ${classResponse.error}")

                val subclassResponse = api.getSubclassByUrl(imageUrl, classResult)
                val subclassResult = subclassResponse.predictedClass
                    ?: throw Exception("Subclass prediction failed: ${subclassResponse.error}")

                className.value = classResult
                subclassName.value = subclassResult
                onSuccess()
                reportImageUrl.value = imageUrl
            } catch (e: Exception) {
                errorMessage.value = e.message
                onError()
            }
        }
    }
}
