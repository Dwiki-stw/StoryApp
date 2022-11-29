package com.example.storyapp.ui.fragment.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.response.MessageLogin
import com.example.storyapp.response.ResponseRegister
import com.google.gson.Gson
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

    fun registerUser(name: String, email: String, password: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<ResponseRegister>{
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    _responseRegister.value = responseBody!!
                    _message.value = responseBody.message
                }
                else{
                    val responseError = Gson().fromJson(response.errorBody()?.charStream(),
                        MessageLogin::class.java)
                    _message.value = responseError.message
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

    companion object{
        private const val TAG = "RegisterViewModel"
    }

}