package com.example.storyapp.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>){

    suspend fun saveData(isLogin: Boolean, name: String, token: String){
        dataStore.edit {
            it[NAME_KEY] = name
            it[LOGIN_KEY] = isLogin
            it[TOKEN_KEY] = token
        }
    }

    fun getStatusLogin(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[LOGIN_KEY] ?: false
    }

    fun getName(): Flow<String> = dataStore.data.map { preferences ->
        preferences[NAME_KEY] ?: ""
    }

    fun getToken(): Flow<String> = dataStore.data.map { preferences ->
        preferences[TOKEN_KEY] ?: ""
    }

    companion object{
        private val LOGIN_KEY = booleanPreferencesKey("login_status")
        private val NAME_KEY = stringPreferencesKey("name_user")
        private val TOKEN_KEY = stringPreferencesKey("token_user")

        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>) : UserPreference{
            return INSTANCE ?: synchronized(this){
                val instance = UserPreference(dataStore)
                INSTANCE = instance
               instance
           }
        }
    }
}