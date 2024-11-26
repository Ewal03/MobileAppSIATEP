package com.example.siatep.preferences

data class User(
    val name: String,
    val token: String,
    val isLogin: Boolean = false
)