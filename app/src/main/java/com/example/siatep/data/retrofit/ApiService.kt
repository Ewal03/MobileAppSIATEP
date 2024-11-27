package com.example.siatep.data.retrofit

import com.example.siatep.data.response.AbsenResponse
import com.example.siatep.data.response.InputAbsenResponse
import com.example.siatep.data.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @POST("getAbsen")
    suspend fun getAbsen(
        @Header("Authorization") token: String,
    ): AbsenResponse

    @FormUrlEncoded
    @POST("inputAbsen")
    suspend fun postAbsen(
        @Field("status") status: String,
        @Field("tgl_absen") tgl_absen: String,
        @Field("id_user") id_user: Int,
        @Field("id_kelas") id_kelas: Int
    ): InputAbsenResponse
}