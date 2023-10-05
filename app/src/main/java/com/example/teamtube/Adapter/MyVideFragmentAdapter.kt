package com.example.teamtube.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamtube.Model.ChannelModel
import com.example.teamtube.databinding.CategoryVideoItemBinding

class MyVideoFragmentAdapter(private val mContext: Context) :
    RecyclerView.Adapter<MyVideoFragmentAdapter.MyVideoViewHolder>() {
    private lateinit var binding : CategoryVideoItemBinding
    var itemsVideo = mutableListOf<ChannelModel>()

    fun setVideoItems(items: List<ChannelModel>) {
        itemsVideo.clear()
        itemsVideo.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyVideoFragmentAdapter.MyVideoViewHolder {
        binding = CategoryVideoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyVideoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemsVideo.size
    }

    override fun onBindViewHolder(
        holder:MyVideoFragmentAdapter.MyVideoViewHolder,
        position: Int
    ) {
        val itemVideo = itemsVideo[position]

        Glide.with(mContext)
            .load(itemVideo.thumbnails)
            .into(holder.img_video)
        holder.tv_video.text = itemVideo.title

        Log.d("Video", "Video = ${itemVideo.title}")
    }

    inner class MyVideoViewHolder(binding: CategoryVideoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val img_video = binding.homeCategoryVideoImage
        val tv_video = binding.homeCategoryVideoTitle
    }

}