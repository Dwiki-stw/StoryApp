package com.example.storyapp.ui.activity.listStory

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

class ListStoryViewModel: ViewModel() {

    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listStory: LiveData<List<ListStoryItem>> = _listStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private fun getListStory(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getStory("Bearer $AUTH")
        client.enqueue(object : Callback<ResponseListStory>{
            override fun onResponse(
                call: Call<ResponseListStory>,
                response: Response<ResponseListStory>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    _listStory.value = responseBody.listStory
                }
                else{
                    Log.e(TAG, "onResponse2: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseListStory>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onResponse2: ${t.message}")
            }

        })
    }

    fun setAuth(auth: String){
        AUTH = auth

        getListStory()
    }

    companion object{
        private const val TAG = "ListStoryViewModel"

        private var AUTH = "auth"
    }


}