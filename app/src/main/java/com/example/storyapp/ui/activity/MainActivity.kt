package com.example.storyapp.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.datastore.UserPreference
import com.example.storyapp.ui.activity.Authentication.AuthenticationActivity
import com.example.storyapp.ui.activity.listStory.ListStoryActivity


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preference")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        var isLogin = true
        val loginState = UserPreference.getInstance(dataStore)
        loginState.getStatusLogin().asLiveData().observe(this){
            isLogin = it
        }


        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({

            if(isLogin){
                val intent = Intent(this, ListStoryActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this@MainActivity, AuthenticationActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 3000)


    }

}
