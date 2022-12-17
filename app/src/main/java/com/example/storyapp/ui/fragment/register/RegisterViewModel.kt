package com.example.storyapp.ui.fragment.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.api.ApiService
import kotlinx.coroutines.launch

class RegisterViewModel(private val apiService: ApiService) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun registerUser(name: String, email: String, password: String, callback: (String) -> Unit){
        _isLoading.value = true
        viewModelScope.launch{
            try{
                val result = apiService.register(name, email, password)
                _isLoading.value = false
                callback(result.message)
            }catch (e: Exception){
                _isLoading.value = false
                Log.e(TAG, "registerUser: ${e.message}", )
                callback("Register Gagal")
            }
        }
    }

    companion object{
        private const val TAG = "RegisterViewModel"
    }

}