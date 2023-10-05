package com.example.teamtube

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.teamtube.Model.HomeitemModel
import com.example.teamtube.databinding.ActivityVideoDetailBinding

class VideoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var detailList = intent.getParcelableExtra<HomeitemModel>("Data")

        detailList?.thumbnails?.let { imageUrl ->
            Glide.with(this)
                .load(imageUrl)
                .into(binding.detailView)
        }
        binding.videoTitle.text = detailList?.title
        binding.detailInfo.text = detailList?.description


        binding.btnShare.setOnClickListener {
            val mytitle = binding.detailTitle.text.toString()
            val mydescription = binding.detailInfo.text.toString()

            val message = "제목: $mytitle\n 상세정보: $mydescription"

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, message)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
}

