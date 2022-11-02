package com.example.storyapp.ui.activity.Authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityAuthenticationBinding
import com.example.storyapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class AuthenticationActivity : AppCompatActivity() {

    private var _binding: ActivityAuthenticationBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val viewPagerAdpater = AuthenticationAdapter(this)
        binding.viewPager.adapter = viewPagerAdpater
        TabLayoutMediator(binding.tablayout, binding.viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.login,
            R.string.register
        )
    }
}