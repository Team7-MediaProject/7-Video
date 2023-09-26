package com.example.teamtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teamtube.databinding.ActivityDetailBinding

class VideoDetailAcitvity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}