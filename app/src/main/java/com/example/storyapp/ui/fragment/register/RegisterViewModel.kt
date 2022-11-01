package com.example.storyapp.ui.fragment.register

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.response.ResponseRegister
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    val responseMessage = MutableLiveData<String>()

    private fun registerUser(){
        val client = ApiConfig.getApiService().register(NAME, EMAIL, PASSWORD)
        client.enqueue(object : Callback<ResponseRegister>{
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    Log.e(TAG, "onResponse1: ${responseBody.message}" )
                    responseMessage.value = responseBody.message!!
                }
                else{
                    responseMessage.value = responseBody?.message ?: "email already"
                    Log.e(TAG, "onResponse2: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                Log.e(TAG, "onResponse3: ${t.message}")
            }

        })
    }

    fun setDataRegister(name: String, email: String, password: String){
        NAME = name
        EMAIL = email
        PASSWORD = password

        registerUser()
    }

    companion object{
        private const val TAG = "RegisterViewModel"

        private var NAME = "name"
        private var EMAIL = "email"
        private var PASSWORD = "password"
    }

}