package com.example.storyapp.ui.activity.detailstory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.response.ResponseDetailStory
import com.example.storyapp.response.Story
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailStoryViewModel: ViewModel() {

    private val _story = MutableLiveData<Story>()
    val story: LiveData<Story> = _story

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getStory(token: String, id: String, callback: (Boolean?, String?) -> Unit){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getStory("Bearer $token", id)
        client.enqueue(object : Callback<ResponseDetailStory>{
            override fun onResponse(
                call: Call<ResponseDetailStory>,
                response: Response<ResponseDetailStory>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    _story.value = responseBody.story!!
                    callback(false, responseBody.message)
                }
                else{
                    Log.e(TAG, "onResponse2: ${response.message()}")
                    callback(true, "gagal memuat")
                }
            }

            override fun onFailure(call: Call<ResponseDetailStory>, t: Throwable) {
                _isLoading.value = false
                callback(true, "gagal memuat")
                Log.e(TAG, "onResponse2: ${t.message}")
            }

        })
    }

    companion object{
        private const val TAG = "DetailStoryViewModel"
    }

}