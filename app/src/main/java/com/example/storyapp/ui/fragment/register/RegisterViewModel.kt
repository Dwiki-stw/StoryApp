package com.example.storyapp.ui.fragment.register

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.response.ResponseRegister
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _responseRegister = MutableLiveData<ResponseRegister>()
    val responseRegister: LiveData<ResponseRegister> = _responseRegister

    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private fun registerUser(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().register(NAME, EMAIL, PASSWORD)
        client.enqueue(object : Callback<ResponseRegister>{
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    Log.e(TAG, "onResponse1: ${responseBody.message}" )
                    _responseRegister.value = responseBody!!
                }
                else{
                    _message.value = response.message()
                    Log.e(TAG, "onResponse2: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                _isLoading.value = false
                _message.value = t.message
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