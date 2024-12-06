package com.example.nott_a_problem.pages.services.image_classification

import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

// Retrofit API interface
interface ImageClassificationApi {
    @POST("classifyByUrl") // Adjust endpoint path as per your API design
    suspend fun getClassByUrl(
        @Query("imageUrl") imageUrl: String // Use imageUrl as a query parameter
    ): ClassificationResponse

    @POST("subclassifyByUrl") // Adjust endpoint path as per your API design
    suspend fun getSubclassByUrl(
        @Query("imageUrl") imageUrl: String, // Use imageUrl as a query parameter
        @Query("className") className: String // Class name as a query parameter
    ): ClassificationResponse
}


// Data class for API responses
data class ClassificationResponse(
    val predictedClass: String?,
    val secondHighestClass: String?,
    val error: String?
)

object ApiService {
    private const val BASE_URL = "https://your-api-base-url.com"

    val api: ImageClassificationApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageClassificationApi::class.java)
    }
}
