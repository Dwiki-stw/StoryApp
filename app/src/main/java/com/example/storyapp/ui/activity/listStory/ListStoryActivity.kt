package com.example.storyapp.ui.activity.listStory


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.adapter.LoadingStateAdapter
import com.example.storyapp.adapter.PagingAdapter
import com.example.storyapp.databinding.ActivityListStoryBinding
import com.example.storyapp.datastore.UserPreference
import com.example.storyapp.helper.ViewModelFactory
import com.example.storyapp.ui.activity.Authentication.AuthenticationActivity
import com.example.storyapp.ui.activity.maps.MapsActivity
import com.example.storyapp.ui.activity.addstory.AddStoryActivity
import com.example.storyapp.ui.activity.detailstory.DetailStoryActivity
import kotlinx.coroutines.launch

class ListStoryActivity : AppCompatActivity() {

    private var _binding: ActivityListStoryBinding? = null
    private val binding get() = _binding!!

    private val listStoryViewModel: ListStoryViewModel by viewModels{
        ViewModelFactory(this)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preference")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showStory()

        binding.fab.setOnClickListener {
            val intent = Intent(this@ListStoryActivity, AddStoryActivity::class.java)
            startActivity(intent)
        }

        binding.fabMap.setOnClickListener {
            val intent = Intent(this@ListStoryActivity, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_menu, menu)

        val user = menu!!.findItem(R.id.name_user)
        val userPreference = UserPreference.getInstance(dataStore)
        userPreference.getName().asLiveData().observe(this){
            user.title = it
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.user_logout -> {
                deleteLoginState()
                val intent = Intent(this, AuthenticationActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
            else -> return true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showStory(){

        val adapter = PagingAdapter()
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )

        listStoryViewModel.listStory.observe(this){
            adapter.submitData(lifecycle, it)
        }

        adapter.setOnItemClickCallback(object: PagingAdapter.OnItemClickCallback{
            override fun onItemClicked(id: String?) {
                val intent = Intent(this@ListStoryActivity, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.ID, id)
                startActivity(intent)
            }

        })
    }

    private fun deleteLoginState(){
        val savePreference = UserPreference.getInstance(dataStore)

        lifecycleScope.launch{
            savePreference.saveData(false, "", "")
        }
    }

}