package com.example.storyapp.ui.activity.detailstory

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityDetailStoryBinding
import com.example.storyapp.datastore.UserPreference
import com.example.storyapp.response.Story

class DetailStoryActivity : AppCompatActivity() {

    private var _binding: ActivityDetailStoryBinding? = null
    private val binding get() = _binding!!

    private val detailStoryViewModel: DetailStoryViewModel by viewModels()

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preference")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(ID).toString()

        val userPreference = UserPreference.getInstance(dataStore)
        userPreference.getToken().asLiveData().observe(this){
            detailStoryViewModel.setID(it, id)
        }

        detailStoryViewModel.story.observe(this){
            setupView(it)
        }

        detailStoryViewModel.isLoading.observe(this){
            showLoading(it)
        }


    }

    private fun setupView(story: Story){
        binding.apply {
            username.text = story.name
            description.text = story.description

            Glide.with(this@DetailStoryActivity)
                .load(story.photoUrl)
                .into(imgStory)
        }
    }

    private fun showLoading(isLoading : Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        const val ID = "id"
    }
}