package com.example.teamtube.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamtube.Model.ChannelModel
import com.example.teamtube.databinding.CategoryChannelItemBinding

class MyVideoFragmentAdapter(private val mContext: Context) :
    RecyclerView.Adapter<MyVideoFragmentAdapter.MyVideoViewHolder>() {

    var itemsChannel = mutableListOf<ChannelModel>()


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
        Log.d("MyVideo","itemChennel.size = ${itemsChannel.size}")
    }

    override fun onBindViewHolder(holder: MyVideoViewHolder, position: Int) {
        val itemChannel = itemsChannel[position]
        holder.bindChannel(itemChannel)

        Glide.with(mContext)
            .load(itemChannel)
            .into((holder as MyVideoViewHolder).img_channel)
        //holder.tv_channel.text = itemsChannel[position].title
    }

    inner class MyVideoViewHolder(private val binding: CategoryChannelItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var img_channel : ImageView = binding.imgChannel
        var like_image: ImageView = binding.likeImageView
        //var tv_channel: TextView = binding.titleChannel
        var channel_item: ConstraintLayout = binding.channelItemConstraintLayout

        fun bindChannel(item: ChannelModel) {
            val LikedList = itemsChannel.map { it.title }
            Log.d("MyVideo","likedItems = $LikedList")
        }
        init {
            like_image.visibility = View.GONE

            channel_item.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    itemsChannel.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }
}