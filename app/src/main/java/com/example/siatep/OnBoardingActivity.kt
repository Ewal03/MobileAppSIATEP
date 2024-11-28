package com.example.siatep

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.siatep.databinding.ActivityOnBoardingBinding
import com.example.siatep.preferences.UserPreferences
import com.example.siatep.preferences.dataStore
import com.example.siatep.ui.auth.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    private lateinit var userPreferences: UserPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences.getInstance(this.dataStore)
        CoroutineScope(Dispatchers.IO).launch {
            updateUi()
        }
        onClick()
    }

    private suspend fun updateUi() {
        val user = userPreferences.getSession().first()
        if (user.isLogin){
            startActivity(Intent(this@OnBoardingActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun onClick() {
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this@OnBoardingActivity, LoginActivity::class.java))
            finish()
        }
    }
}