package com.example.storyapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.storyapp.Database.StoryDatabase
import com.example.storyapp.api.ApiService
import com.example.storyapp.datastore.UserPreference
import com.example.storyapp.response.ListStoryItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import androidx.room.withTransaction

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(
    private val database: StoryDatabase,
    private val apiService: ApiService,
    private val context: Context
): RemoteMediator<Int, ListStoryItem>() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preference")

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ListStoryItem>
    ): MediatorResult {
        val page = INITIAL_PAGE_INDEX
        val pref = context.dataStore
        val dataPreference = UserPreference.getInstance(pref)
        val tokenAuth = runBlocking { dataPreference.getToken().first() }

        try {
            val responseData = apiService.getAllStory("Bearer $tokenAuth", page, state.config.pageSize)
            val listStory = responseData.listStory

            val endOfPaginationReached = listStory.isEmpty()

            database.withTransaction{
                if (loadType == LoadType.REFRESH){
                    database.storyDao().deleteAll()
                }
                database.storyDao().insertStory(listStory)
            }

            return MediatorResult.Success(endOfPaginationReached)
        }catch (exception: Exception){
            return MediatorResult.Error(exception)
        }
    }

    companion object{
        const val INITIAL_PAGE_INDEX = 1
    }
}