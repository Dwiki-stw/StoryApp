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

    private fun getStory(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getStory("Bearer $AUTH", ID)
        client.enqueue(object : Callback<ResponseDetailStory>{
            override fun onResponse(
                call: Call<ResponseDetailStory>,
                response: Response<ResponseDetailStory>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    _story.value = responseBody.story!!
                }
                else{
                    Log.e(TAG, "onResponse2: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseDetailStory>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onResponse2: ${t.message}")
            }

        })
    }

    fun setID(auth: String, id: String){
        AUTH = auth
        ID = id

        getStory()
    }

    companion object{
        private const val TAG = "DetailStoryViewModel"

        private var AUTH = "auth"
        private var ID = "id"
    }

}