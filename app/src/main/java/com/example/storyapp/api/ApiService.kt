package com.example.storyapp.api

import com.example.storyapp.response.ResponseListStory
import com.example.storyapp.response.ResponseLogin
import com.example.storyapp.response.ResponseRegister
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
    fun getStory(
        @Header("Authorization") auth: String
    ): Call<ResponseListStory>

}