package com.example.siatep


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.example.siatep.databinding.ActivityScanqrBinding


class ScanqrActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanqrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanqrBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}