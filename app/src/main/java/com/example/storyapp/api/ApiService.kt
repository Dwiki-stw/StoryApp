package com.example.storyapp.api

import com.example.storyapp.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): ResponseRegister

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): ResponseLogin

    @GET("stories")
    suspend fun getAllStory(
        @Header("Authorization") auth: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ResponseListStory

    @GET("stories")
    suspend fun getLocation(
        @Header("Authorization") auth: String,
        @Query("location") location: Int
    ): ResponseListStory

    @Multipart
    @POST("stories")
    fun addStory(
        @Header("Authorization") auth: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Float,
        @Part("lon") lon: Float
    ): Call<ResponseAddStory>

    @GET("stories/{id}")
    suspend fun getStory(
        @Header("Authorization") auth: String,
        @Path("id") id: String
    ): ResponseDetailStory

}