package com.example.storyapp.api

import com.example.storyapp.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseRegister>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseLogin>

    @GET("stories")
    fun getAllStory(
        @Header("Authorization") auth: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ResponseListStory

    @GET("stories")
    fun getLocation(
        @Header("Authorization") auth: String,
        @Query("location") location: Int
    ): Call<ResponseListStory>

    @Multipart
    @POST("stories")
    fun addStory(
        @Header("Authorization") auth: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<ResponseAddStory>

    @GET("stories/{id}")
    fun getStory(
        @Header("Authorization") auth: String,
        @Path("id") id: String
    ): Call<ResponseDetailStory>

}