package com.example.siatep.di

import android.content.Context
import com.example.siatep.data.retrofit.ApiConfig
import com.example.siatep.preferences.UserPreferences
import com.example.siatep.preferences.dataStore
import com.example.siatep.repository.SiatepRepository

object Injection {
    fun provideRepository(context: Context): SiatepRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return SiatepRepository.getInstance(pref, apiService)
    }
}