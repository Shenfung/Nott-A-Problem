package com.example.nott_a_problem.repository

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageClassificationApi {

    @Multipart
    @POST("get_class")
    suspend fun getClassByFile(
        @Part image: MultipartBody.Part
    ): ClassificationResponse

    @Multipart
    @POST("get_subclass")
    suspend fun getSubclassByFile(
        @Part image: MultipartBody.Part,
        @Part("class_name") className: RequestBody
    ): ClassificationResponse
}

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
