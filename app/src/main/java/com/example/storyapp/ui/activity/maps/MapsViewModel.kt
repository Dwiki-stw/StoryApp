package com.example.storyapp.ui.activity.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storyapp.api.ApiService
import com.example.storyapp.response.ResponseListStory
import kotlinx.coroutines.launch

class MapsViewModel(private val apiService: ApiService) : ViewModel() {

    private val _storyWithLocation = MutableLiveData<ResponseListStory>()
    val storyWithLocation: LiveData<ResponseListStory> = _storyWithLocation


    fun getLocationStory(token: String, location: Int, callback: (Boolean, String) -> Unit){
        viewModelScope.launch {
            try {
                val result = apiService.getLocation("Bearer $token", location)
                _storyWithLocation.value = result
                callback(result.error!!, result.message!!)
            }catch (e: Exception){
                Log.e(TAG, "getLocationStory: ${e.message}", )
                callback(true, "Gagal memuat lokasi Story")
            }
        }
    }


    companion object{
        private const val TAG = "MapsViewModel"
    }

}