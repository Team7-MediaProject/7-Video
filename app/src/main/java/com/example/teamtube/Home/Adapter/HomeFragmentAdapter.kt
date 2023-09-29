package com.example.teamtube.Home.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamtube.HomeData
import com.example.teamtube.R
import com.example.teamtube.Retrofit.Model.VideoItem
import com.example.teamtube.databinding.CategoryChannelItemBinding
import com.example.teamtube.databinding.CategoryVideoItemBinding
import com.example.teamtube.databinding.MostPopularItemBinding
import com.example.teamtube.model.HomeitemModel

class HomeFragmentAdapter(private val mContext: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding1: MostPopularItemBinding
    private lateinit var binding2: CategoryChannelItemBinding
    private lateinit var binding3: CategoryVideoItemBinding

    private val mItems = mutableListOf<VideoItem>()
    private val homeData = mutableListOf<HomeData>()
    private val TYPE_FAVORTIE = 0
    private val TYPE_CHANNEL = 1
    private val TYPE_VIDEO = 2
    private val items: MutableList<HomeitemModel> = mutableListOf()

    fun setVideoItems(videoItem: List<VideoItem>) {
        mItems.clear()
        mItems.addAll(videoItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        // viewType에 따른 viewHolder 반환
        return when (viewType) {
            TYPE_FAVORTIE -> {
                binding1 = MostPopularItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                VideoViewHolder(binding1)
            }

            TYPE_CHANNEL -> {
                binding2 = CategoryChannelItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ChannelHolder(binding2)
            }

            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.category_video_item, parent, false)
                VideoHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    /*            override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
                holder.bind(item)
            }*/

    // viewType에 따른 데이터 binding 방식 변경
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemType = homeData[position].type
        val item = items[position]

        when (itemType) {
            TYPE_FAVORTIE -> {
                (holder as VideoViewHolder).bind(item)
                holder.setIsRecyclable(false)
            }

            TYPE_CHANNEL -> {
                //(holder as ChannelHolder).bind(mItems[position])
                holder.setIsRecyclable(false)
            }

            TYPE_VIDEO -> {
                (holder as VideoHolder).bind(mItems[position])
                holder.setIsRecyclable(false)
            }
        }
    }

    inner class VideoViewHolder(private val binding1: MostPopularItemBinding) :
        RecyclerView.ViewHolder(binding1.root) {
        fun bind(item: HomeitemModel) {
            binding1.scInfo.text = item.title

            Glide.with(mContext)
                .load(item.thumbnails)
                .into(binding1.imgMpv)
        }
    }

    inner class ChannelHolder(private val binding2: CategoryChannelItemBinding) :
        RecyclerView.ViewHolder(binding2.root) {


    }

    inner class VideoHolder(view: View) : RecyclerView.ViewHolder(view) {
        val video_image = binding3.homeCategoryVideoImage
        val video_title = binding3.homeCategoryVideoTitle

        fun bind(item: VideoItem) {
            // api 이용할  Glide로 추후 변경
            val videoItems = item.snippet.thumbnails
            if (videoItems != null) {
                val thumnailUrl = videoItems.high
                Glide.with(mContext)
                    .load(thumnailUrl)
                    .into(video_image)
            }
            video_title.text = item.snippet.title
        }

    }
    fun updateData(newItems: List<HomeitemModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}