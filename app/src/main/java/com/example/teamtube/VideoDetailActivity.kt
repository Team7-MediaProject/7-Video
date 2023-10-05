package com.example.teamtube

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.teamtube.Model.HomeitemModel
import com.example.teamtube.databinding.ActivityVideoDetailBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoDetailBinding
    private lateinit var youTubePlayerView: YouTubePlayerView
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        youTubePlayerView = binding.detailView
        lifecycle.addObserver(youTubePlayerView)

        val detailList = intent.getParcelableExtra<HomeitemModel>("Data")

        detailList?.id?.let { videoId ->
            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    youTubePlayer.loadVideo(videoId, 0f)
                }

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer,
                    state: PlayerConstants.PlayerState
                ) {
                    super.onStateChange(youTubePlayer, state)
                    if(state == PlayerConstants.PlayerState.ENDED) {
                        finish()
                    }
                }
            })
        }
        binding.videoTitle.text = detailList?.title
        binding.detailInfo.text = detailList?.description


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

