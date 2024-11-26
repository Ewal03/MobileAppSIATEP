package com.example.siatep.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("user")
	val user: User
)

data class User(

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("token")
	val token: String
)
