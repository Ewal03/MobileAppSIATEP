package com.example.siatep

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.siatep.databinding.ActivityDetailBinding
import com.example.siatep.databinding.ActivityOnBoardingBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("NAME")
        val status = intent.getStringExtra("STATUS")
        val kelas = intent.getStringExtra("KELAS")
        val tglAbsen = intent.getStringExtra("TGL_ABSEN")

        binding.tvStatus.text = status
        binding.tvNama.text = name
        binding.tvKelas.text = kelas
        binding.tvCreated.text = tglAbsen

        animateCardView()
    }
    private fun animateCardView() {
        // Animasi Slide Up
        val slideUp = ObjectAnimator.ofFloat(binding.main, "translationY", 1000f, 0f)
        slideUp.duration = 800

        // Animasi Fade In
        val fadeIn = ObjectAnimator.ofFloat(binding.main, "alpha", 0f, 1f)
        fadeIn.duration = 800

        // Kombinasi Animasi
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(slideUp, fadeIn)
        animatorSet.start()
    }
}