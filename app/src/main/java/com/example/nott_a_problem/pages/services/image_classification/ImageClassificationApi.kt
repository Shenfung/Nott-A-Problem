package com.example.nott_a_problem.pages.services.image_classification

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.io.File

// Retrofit API interface with Multipart support for uploading files
interface ImageClassificationApi {
    @Multipart
    @POST("get_class") // Change endpoint to reflect file upload
    suspend fun getClassByFile(
        @Part image: MultipartBody.Part // Uploading a file
    ): ClassificationResponse

    @Multipart
    @POST("get_class_second") // Change endpoint to reflect file upload
    suspend fun getSecondClassByFile(
        @Part image: MultipartBody.Part // Uploading a file
    ): ClassificationResponse

    @Multipart
    @POST("get_subclass") // Change endpoint to reflect file upload
    suspend fun getSubclassByFile(
        @Part image: MultipartBody.Part, // Image file
        @Part("class_name") className: RequestBody // Optional class name as a query parameter
    ): ClassificationResponse

    @Multipart
    @POST("get_subclass_second") // Change endpoint to reflect file upload
    suspend fun getSecondSubclassByFile(
        @Part image: MultipartBody.Part, // Image file
        @Query("secondHighestClassName") className: String // Optional class name as a query parameter
    ): ClassificationResponse
}


// Data class for API responses
data class ClassificationResponse(
    @SerializedName("predicted class") val predictedClass: String?,
    @SerializedName("second highest class") val secondHighestClass: String?,
    @SerializedName("error") val error: String?
)


object ApiService {
    private const val BASE_URL = "https://api-vd42zjxz4a-as.a.run.app/"


    val api: ImageClassificationApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageClassificationApi::class.java)
    }
}
