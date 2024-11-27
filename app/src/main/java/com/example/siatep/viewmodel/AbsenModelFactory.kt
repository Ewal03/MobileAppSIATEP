package com.example.siatep.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.siatep.di.Injection
import com.example.siatep.repository.SiatepRepository
import com.example.siatep.ui.auth.LoginViewModel
import com.example.siatep.ui.home.HomeViewModel

class AbsenModelFactory private constructor(private val siatepRepository: SiatepRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(siatepRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: AbsenModelFactory? = null
        fun getInstance(context: Context): AbsenModelFactory =
            instance ?: synchronized(this) {
                instance ?: AbsenModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}