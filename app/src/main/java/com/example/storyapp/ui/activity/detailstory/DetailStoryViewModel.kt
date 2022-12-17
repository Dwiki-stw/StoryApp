package com.example.storyapp.ui.activity.detailstory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.api.ApiService
import com.example.storyapp.response.ResponseDetailStory
import kotlinx.coroutines.launch


class DetailStoryViewModel(private val apiService: ApiService): ViewModel() {

    private val _story = MutableLiveData<ResponseDetailStory>()
    val story: LiveData<ResponseDetailStory> = _story

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getStory(token: String, id: String, callback: (Boolean, String) -> Unit){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = apiService.getStory("Bearer $token", id)
                _story.value = result
                _isLoading.value = false
                callback(false, result.message!!)

            }catch (e : Exception){
                Log.e(TAG, "getStory: ${e.message}")
                _isLoading.value = false
                callback(true, "Gagal memuat story")
            }
        }
    }

    companion object{
        private const val TAG = "DetailStoryViewModel"
    }

}