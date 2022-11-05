package com.example.storyapp.ui.activity.listStory


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityListStoryBinding
import com.example.storyapp.ui.fragment.AddStoryFragment

class ListStoryActivity : AppCompatActivity() {

    private var _binding: ActivityListStoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fab.setOnClickListener {
            val  mfragmentManager = supportFragmentManager
            val addStoryFragment = AddStoryFragment()
            val fragment = mfragmentManager.findFragmentByTag(AddStoryFragment::class.java.simpleName)

            if (fragment !is AddStoryFragment){
                mfragmentManager
                    .beginTransaction()
                    .add(androidx.fragment.R.id.fragment_container_view_tag, addStoryFragment, AddStoryFragment::class.java.simpleName)
                    .commit()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_menu, menu)

        return true
    }
}