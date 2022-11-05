package com.example.storyapp.ui.fragment.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.response.ResponseLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<ResponseLogin>()
    val loginResult: LiveData<ResponseLogin> = _loginResult

    private fun loginUser(){
        val client = ApiConfig.getApiService().login(EMAIL, PASSWORD)
        client.enqueue(object : Callback<ResponseLogin>{
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    _loginResult.value = responseBody!!
                }
                else{
                    Log.e(TAG, "onResponse2: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e(TAG, "onResponse3: ${t.message}")
            }

        })
    }

    fun setDataLogin(email: String, password: String){
        EMAIL = email
        PASSWORD = password

        loginUser()
    }


    companion object{
        private const val TAG = "LoginViewModel"

        private var EMAIL = "email"
        private var PASSWORD = "password"
    }
}