package com.example.siatep

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
    }
}