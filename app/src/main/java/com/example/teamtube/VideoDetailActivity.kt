package com.example.teamtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.teamtube.Model.HomeitemModel
import com.example.teamtube.databinding.ActivityVideoDetailBinding

class VideoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoDetailBinding
    private var isToggled = false
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
        binding.btnLike.text = "UNLIKE"

        binding.btnLike.setOnClickListener {
            isToggled = !isToggled
            if(isToggled) {
                binding.btnLike.text = "LIKE"
                binding.btnLike.setBackgroundResource(R.drawable.video_like)
            } else {
                binding.btnLike.text = "UNLIKE"
                binding.btnLike.setBackgroundResource(R.drawable.video_unlike)
            }
        }
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
