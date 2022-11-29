package com.example.storyapp.ui.activity.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.response.ListStoryItem
import com.example.storyapp.response.ResponseListStory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel : ViewModel() {

    private val _storyWithLocation = MutableLiveData<List<ListStoryItem>>()
    val storyWithLocation: LiveData<List<ListStoryItem>> = _storyWithLocation


    fun getLocationStory(token: String){
        val client = ApiConfig.getApiService().getLocation("Bearer $token", 1)
        client.enqueue(object : Callback<ResponseListStory> {
            override fun onResponse(
                call: Call<ResponseListStory>,
                response: Response<ResponseListStory>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    _storyWithLocation.value = responseBody.listStory
                }
            }

            override fun onFailure(call: Call<ResponseListStory>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }


    companion object{
        private const val TAG = "MapsViewModel"
    }

}