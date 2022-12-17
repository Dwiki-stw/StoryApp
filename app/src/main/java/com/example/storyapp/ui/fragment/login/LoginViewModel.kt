package com.example.storyapp.ui.fragment.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.api.ApiService
import com.example.storyapp.response.ResponseLogin
import kotlinx.coroutines.launch

class LoginViewModel(private val apiService: ApiService) : ViewModel() {

    private val _loginResult = MutableLiveData<ResponseLogin>()
    val loginResult: LiveData<ResponseLogin> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun loginUser(email: String, password: String, callback: (String) -> Unit){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = apiService.login(email, password)
                _loginResult.value = result
                _isLoading.value = false
                callback(result.message)

            }catch (e: Exception){
                Log.e(TAG, "loginUser: ${e.message}")
                _isLoading.value = false
                callback("Login gagal")
            }
        }
//        client.enqueue(object : Callback<ResponseLogin>{
//            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
//                _isLoading.value = false
//                val responseBody = response.body()
//                if (response.isSuccessful && responseBody != null){
//                    _loginResult.value = responseBody!!
//                    callback(responseBody.message)
//                }else{
//                    val responseError = Gson().fromJson(response.errorBody()?.charStream(), MessageLogin::class.java)
//                    callback(responseError.message)
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message}", )
//            }
//
//        })
    }

    companion object{
        private const val TAG = "LoginViewModel"
    }
}