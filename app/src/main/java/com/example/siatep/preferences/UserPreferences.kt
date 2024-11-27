package com.example.siatep.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>){

    suspend fun saveSession(user: User){
        dataStore.edit { preferences ->
            preferences[ID] = user.id
            preferences[NAME_KEY] = user.name
            preferences[ID_KELAS] = user.id_kelas
            preferences[EMAIL] = user.email
            preferences[TOKEN_KEY] = user.token
            preferences[LOGIN_STATE] = user.isLogin
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[ID]?: 0,
                preferences[NAME_KEY] ?: "",
                preferences[ID_KELAS]?: 0,
                preferences[EMAIL] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun logout(){
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: UserPreferences? = null

        private val ID = intPreferencesKey("id")
        private val EMAIL = stringPreferencesKey("email")
        private val TGLABSEN = stringPreferencesKey("tgl_absen")
        private val ID_KELAS = intPreferencesKey("id_kelas")
        private val NAME_KEY = stringPreferencesKey("name")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val LOGIN_STATE = booleanPreferencesKey("state")
        private val IS_LOGIN_KEY = booleanPreferencesKey("islogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences{
            return INSTANCE ?: synchronized(this){
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}