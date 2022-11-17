package com.example.storyapp.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.storyapp.api.ApiService
import com.example.storyapp.datastore.UserPreference
import com.example.storyapp.response.ListStoryItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class StoryPagingSource(private val apiService: ApiService, private val context: Context): PagingSource<Int, ListStoryItem>() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preference")

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        val pref = context.dataStore
        val dataPreference = UserPreference.getInstance(pref)
        val tokenAuth = runBlocking { dataPreference.getToken().first() }



        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllStory("Bearer $tokenAuth", position, params.loadSize)
            val listStory = responseData.listStory

            LoadResult.Page(
                data = listStory,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position-1,
                nextKey = if(listStory.isEmpty()) null else position + 1
            )
        }catch (exception: Exception){
            Log.d(TAG, "load: error")
            return LoadResult.Error(exception)
        }
    }

    private companion object{
        const val INITIAL_PAGE_INDEX = 1
        const val TAG = "StoryPagingSource"
    }
}