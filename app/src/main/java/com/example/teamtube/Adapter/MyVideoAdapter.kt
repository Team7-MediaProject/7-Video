package com.example.teamtube.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamtube.Model.ChannelModel
import com.example.teamtube.Model.HomeitemModel
import com.example.teamtube.databinding.CategoryVideoItemBinding

class MyVideoAdapter(private val mContext: Context) :
    RecyclerView.Adapter<MyVideoAdapter.MyVideoViewHolder>() {

    var itemsVideo = mutableListOf<HomeitemModel>()

    fun setVideoItems(list: List<HomeitemModel>) {
        itemsVideo = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVideoViewHolder {
        val binding = CategoryVideoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyVideoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemsVideo.size
    }

    override fun onBindViewHolder(holder: MyVideoViewHolder, position: Int) {
        val itemVideo = itemsVideo[position]

        Glide.with(mContext)
            .load(itemVideo.thumbnails)
            .into(holder.img_video)
        holder.tv_video.text = itemVideo.title

        Log.d("Video", "Video = ${itemVideo.title}")
    }

    inner class MyVideoViewHolder(binding: CategoryVideoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var img_video: ImageView = binding.homeCategoryVideoImage
        var tv_video: TextView = binding.homeCategoryVideoTitle

    }
}