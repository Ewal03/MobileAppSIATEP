package com.example.siatep.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.siatep.MainActivity
import com.example.siatep.databinding.ActivityLoginBinding
import com.example.siatep.preferences.User
import com.example.siatep.preferences.UserPreferences
import com.example.siatep.preferences.dataStore
import com.example.siatep.viewmodel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPreferences: UserPreferences
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        userPreferences = UserPreferences.getInstance(this.dataStore)

        loginViewModel.isLoading.observe(this){
            showLoading(it)
        }

        postLogin()
        loginViewModel.login.observe(this){response ->
            val message = "Login Success"
            if (response.error == false){
                CoroutineScope(Dispatchers.Main).launch {
                    val saveToken = async(Dispatchers.IO) {
                        userPreferences.saveSession(
                            User(
                                response.user.id,
                                response.user.name,
                                response.user.idKelas,
                                response.user?.email.toString(),
                                AUTH + (response.user?.token.toString()),
                                true
                            )
                        )
                    }
                    saveToken.await()

                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
        playAnimation()
    }
    private fun postLogin(){
        binding.btnLogin.setOnClickListener{
            loginViewModel.postLogin(
                binding.edEmail.text.toString(),
                binding.edPassword.text.toString()
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageLogin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.title, View.ALPHA, 1f).setDuration(100)
        val message =
            ObjectAnimator.ofFloat(binding.subtitle, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.edEmail, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.pwTv, View.ALPHA, 1f).setDuration(100)
        val passwordTextViewLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.edPassword, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordTextViewLayout,
                passwordEditTextLayout,
                login
            )
            startDelay = 100
        }.start()
    }

    companion object{
        private const val AUTH = "Bearer "
    }
}