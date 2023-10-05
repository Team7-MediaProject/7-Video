package com.example.teamtube

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.teamtube.Adapter.MyVideoAdapter
import com.example.teamtube.Fragment.MyVideoFragment
import com.example.teamtube.Model.ChannelModel
import com.example.teamtube.Model.HomeitemModel
import com.example.teamtube.databinding.ActivityVideoDetailBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.lang.reflect.Type

class VideoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoDetailBinding
    private lateinit var youTubePlayerView: YouTubePlayerView
    private var isToggled = false
    var likedItems: ArrayList<HomeitemModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        youTubePlayerView = binding.detailView
        lifecycle.addObserver(youTubePlayerView)

        val detailList = intent.getParcelableExtra<HomeitemModel>("Data")

        binding.videoTitle.text = detailList?.title
        binding.detailInfo.text = detailList?.description
        binding.btnLike.text = "UNLIKE"

        binding.btnLike.setOnClickListener {
            isToggled = !isToggled
            if (isToggled) {
                binding.btnLike.text = "LIKE"
                binding.btnLike.setBackgroundResource(R.drawable.video_like)

                val thumbnails = detailList?.thumbnails
                val title = detailList?.title
                Log.d("likedVideo", "$thumbnails, $title")

                val sharedPreferences = getSharedPreferences("Video", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                val gson = Gson()
                val json: String = gson.toJson(detailList)

                editor.putString(title, json)
                editor.apply()
            } else {
                binding.btnLike.text = "UNLIKE"
                binding.btnLike.setBackgroundResource(R.drawable.video_unlike)
            }
        }

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

