package com.example.siatep.preferences

data class User(
    val id: Int,
    val name: String,
    val id_kelas: Int,
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)