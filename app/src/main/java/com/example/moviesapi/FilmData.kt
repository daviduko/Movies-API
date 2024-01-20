package com.example.moviesapi

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesapi.databinding.FilmDataLayoutBinding

class FilmData : AppCompatActivity() {

    private lateinit var binding: FilmDataLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FilmDataLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val synopsis = intent.getStringExtra("synopsis")
        binding.synopsisData.text = synopsis

        binding.backButton.setOnClickListener { finish() }
    }
}