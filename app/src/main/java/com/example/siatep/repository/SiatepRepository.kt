package com.example.siatep.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.siatep.data.response.AbsenResponse
import com.example.siatep.data.response.InputAbsenResponse
import com.example.siatep.data.response.LoginResponse
import com.example.siatep.data.retrofit.ApiService
import com.example.siatep.preferences.User
import com.example.siatep.preferences.UserPreferences

class SiatepRepository(private val userPreferences: UserPreferences, private val apiService: ApiService) {

    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.postLogin(email,password)
    }

    suspend fun getAbsen(token: String): AbsenResponse{
        return apiService.getAbsen(token)
    }

    fun getSession(): LiveData<User> {
        return userPreferences.getSession().asLiveData()
    }

    suspend fun postAbsen(status: String, tgl_absen: String, id_user: Int, id_kelas: Int): InputAbsenResponse{
        return apiService.postAbsen(status,tgl_absen, id_user, id_kelas)
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