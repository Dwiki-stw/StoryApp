package com.example.storyapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.ui.activity.Authentication.AuthenticationActivity


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this@MainActivity, AuthenticationActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)


    }
}