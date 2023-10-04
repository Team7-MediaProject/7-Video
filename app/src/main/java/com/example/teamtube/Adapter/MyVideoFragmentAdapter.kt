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
import com.example.teamtube.MainActivity
import com.example.teamtube.Model.ChannelModel
import com.example.teamtube.databinding.CategoryChannelItemBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class MyVideoFragmentAdapter(private val mContext: Context) :
    RecyclerView.Adapter<MyVideoFragmentAdapter.MyVideoViewHolder>() {
    var itemsChannel= mutableListOf<ChannelModel>()

    fun setItems(list: List<ChannelModel>) {
        itemsChannel = list.toMutableList()
        notifyDataSetChanged()
    }
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

        Glide.with(mContext)
            .load(itemChannel.thumbnails)
            .into(holder.img_channel)
        holder.tv_channel.text = itemChannel.title

        Log.d("Video", "Video = ${itemChannel.title}")
    }

    inner class MyVideoViewHolder(binding: CategoryChannelItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var img_channel : ImageView = binding.imgChannel
        var like_image: ImageView = binding.likeImageView
        var tv_channel: TextView = binding.titleChannel
        var channel_item: ConstraintLayout = binding.channelItemConstraintLayout

        init {

            channel_item.setOnClickListener {
                val position = adapterPosition
                (mContext as MainActivity).removeLikedItem(itemsChannel[position])

                if(position != RecyclerView.NO_POSITION) {
                    itemsChannel.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }
    }
}