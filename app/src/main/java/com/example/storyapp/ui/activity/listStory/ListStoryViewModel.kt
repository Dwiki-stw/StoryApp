package com.example.storyapp.ui.activity.listStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.StoryRepository
import com.example.storyapp.response.ListStoryItem

class ListStoryViewModel(private val repository: StoryRepository): ViewModel() {

    val listStory: LiveData<PagingData<ListStoryItem>> =
        repository.getStory().cachedIn(viewModelScope)

    companion object{
        private const val TAG = "ListStoryViewModel"
    }


}