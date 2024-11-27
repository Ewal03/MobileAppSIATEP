package com.example.siatep.ui.home


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.siatep.data.response.AbsenResponse
import com.example.siatep.data.response.ErrorResponse
import com.example.siatep.preferences.User
import retrofit2.HttpException
import com.example.siatep.repository.SiatepRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch

class HomeViewModel(private val siatepRepository: SiatepRepository): ViewModel() {

   private val _absen = MutableLiveData<AbsenResponse>()
    val absen: LiveData<AbsenResponse> = _absen

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAbsen(token: String){
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val response = siatepRepository.getAbsen(token)
                _absen.postValue(response)
                _isLoading.postValue(false)
                Log.d("Get History","${response.data}")
            }catch (e: HttpException){
                _isLoading.postValue(false)
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                Log.d("Get History","error")
            }
        }
    }
    fun postAbsen(status: String, tgl_absen: String, id_user: Int, id_kelas: Int){
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val response = siatepRepository.postAbsen(status, tgl_absen, id_user, id_kelas)
                _isLoading.postValue(false)
                Log.d("Post","ANjay")
            }catch (e: HttpException){
                _isLoading.postValue(false)
//                val jsonInString = e.response()?.errorBody()?.string()
//                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
//                val errorMessage = errorBody.message
                Log.d("Get History","${e.response()}")
            }
        }
    }
    fun getSession(): LiveData<User> {
        return siatepRepository.getSession()
    }
}