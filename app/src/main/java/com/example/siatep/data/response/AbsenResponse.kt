package com.example.siatep.data.response

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class AbsenResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: Boolean
)
data class InputAbsenResponse(
	@field:SerializedName("message")
	val message: Boolean
)

data class DataItem(

	@field:SerializedName("updated_at")
	val updatedAt: Any,

	@field:SerializedName("kelas")
	val kelas: String,

	@field:SerializedName("tgl_absen")
	val tglAbsen: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: Any,

	@field:SerializedName("id_kelas")
	val idKelas: Int,

	@field:SerializedName("id_absen")
	val idAbsen: Int,

	@field:SerializedName("id_user")
	val idUser: Int,

	@field:SerializedName("status")
	val status: String
)
