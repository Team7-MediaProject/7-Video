package com.example.teamtube.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamtube.ChannelData.ChannelModel
import com.example.teamtube.databinding.CategoryChannelItemBinding

class MyVideoFragmentAdapter(private val mContext: Context) :
    RecyclerView.Adapter<MyVideoFragmentAdapter.MyVideoViewHolder>() {

    var itemsChannel = ArrayList<ChannelModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVideoViewHolder {
        val binding = CategoryChannelItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyVideoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemsChannel.size
    }

    override fun onBindViewHolder(holder: MyVideoViewHolder, position: Int) {
        val itemChannel = itemsChannel[position]
        holder.bindChannel(itemChannel)
    }

    inner class MyVideoViewHolder(private val binding: CategoryChannelItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindChannel(item: ChannelModel) {

            val img_channel : ImageView = binding.imgChannel
            val title_channel : TextView = binding.titleChannel//            var like_image: ImageView = binding.likeImageView
//            var channel_item: ConstraintLayout = binding.channelItemConstraintLayout

            Glide.with(mContext)
                .load(item.thumbnails)
                .into(img_channel)

            title_channel.text = item.title
        }
    }
}