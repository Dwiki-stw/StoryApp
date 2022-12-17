package com.example.storyapp.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.di.Injection
import com.example.storyapp.ui.activity.detailstory.DetailStoryViewModel
import com.example.storyapp.ui.activity.listStory.ListStoryViewModel
import com.example.storyapp.ui.activity.maps.MapsViewModel
import com.example.storyapp.ui.fragment.login.LoginViewModel
import com.example.storyapp.ui.fragment.register.RegisterViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListStoryViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ListStoryViewModel(Injection.provideRepository(context)) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(Injection.provideApiService()) as T
        }
        if(modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(Injection.provideApiService()) as T
        }
        if(modelClass.isAssignableFrom(MapsViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MapsViewModel(Injection.provideApiService()) as T
        }
        if (modelClass.isAssignableFrom(DetailStoryViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return DetailStoryViewModel(Injection.provideApiService()) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class")
    }
}