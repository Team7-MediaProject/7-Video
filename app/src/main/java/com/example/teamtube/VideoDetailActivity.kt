package com.example.teamtube

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


//        detailList?.id?.let { imageUrl ->
//            Glide.with(this)
//                .load(imageUrl)
//                .into(binding.detailView)
//        }
//        binding.videoTitle.text = detailList?.title
//        binding.detailInfo.text = detailList?.description
    }

//        videoList()
//        categoryList()
//        searchList()
//
//    }
//
//    private fun videoList() {
//        var detailList = intent.getParcelableExtra<HomeitemModel>("Data")
//
//        detailList?.thumbnails?.let{ imageUrl ->
//            Glide.with(this)
//                .load(imageUrl)
//                .into(binding.detailView)
//        }
//        binding.videoTitle.text = detailList?.title
//        binding.detailInfo.text = detailList?.description
//    }
//
//    private fun categoryList() {
//        var de_List = intent.getParcelableExtra<CategoryList>("tail")
//
//        de_List?.image?.let{ imageUrl ->
//            Glide.with(this)
//                .load(imageUrl)
//                .into(binding.detailView)
//        }
//        binding.videoTitle.text = de_List?.categoryTitle
//        binding.detailInfo.text = de_List?.description
//    }
//
//    private fun searchList() {
//        var search = intent.getParcelableExtra<SearchData>("search")
//
//        search?.thumbnails?.let { imageUrl ->
//            Glide.with(this)
//                .load(imageUrl)
//                .into(binding.detailView)
//        }
//    }

}
