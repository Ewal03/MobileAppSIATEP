package com.example.siatep.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.siatep.data.response.LoginResponse
import com.example.siatep.data.retrofit.ApiService
import com.example.siatep.preferences.User
import com.example.siatep.preferences.UserPreferences

class SiatepRepository(private val userPreferences: UserPreferences, private val apiService: ApiService) {

    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.postLogin(email,password)
    }

    fun getSession(): LiveData<User> {
        return userPreferences.getSession().asLiveData()
    }


    companion object{
        @Volatile
        private var instance: SiatepRepository? = null

        fun getInstance(
            userPreferences: UserPreferences,
            apiService: ApiService
        ): SiatepRepository =
            instance ?: synchronized(this) {
                instance ?: SiatepRepository(userPreferences, apiService)
            }.also { instance = it }
    }
}