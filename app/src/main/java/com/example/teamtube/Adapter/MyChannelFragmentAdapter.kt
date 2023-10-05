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
import com.example.teamtube.databinding.CategoryChannelItemBinding

class MyChannelFragmentAdapter(private val mContext: Context) :
        RecyclerView.Adapter<MyChannelFragmentAdapter.MyChannelViewHolder>() {
        var itemsChannel= mutableListOf<ChannelModel>()

        fun setChannelItems(list: List<ChannelModel>) {
            itemsChannel = list.toMutableList()
            notifyDataSetChanged()
        }
    //    fun setVideoItems(thumbnail: String, title: String) {
    //        val item = HomeitemModel(thumbnail, title)
    //
    //        notifyDataSetChanged()
    //    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyChannelViewHolder {
            val binding = CategoryChannelItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return MyChannelViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return itemsChannel.size
        }

        override fun onBindViewHolder(holder: MyChannelViewHolder, position: Int) {
            val itemChannel = itemsChannel[position]

            Glide.with(mContext)
                .load(itemChannel.thumbnails)
                .into(holder.img_channel)
            holder.tv_channel.text = itemChannel.title

            Log.d("Video", "Video = ${itemChannel.title}")

        }

        inner class MyChannelViewHolder(binding: CategoryChannelItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

            var img_channel : ImageView = binding.imgChannel
            var tv_channel: TextView = binding.titleChannel

    //        init {
    //
    //            channel_item.setOnClickListener {
    //                val position = adapterPosition
    //                (mContext as MainActivity).removeLikedItem(itemsChannel[position])
    //
    //                if(position != RecyclerView.NO_POSITION) {
    //                    itemsChannel.removeAt(position)
    //                    notifyItemRemoved(position)
    //                }
    //            }
    //        }
        }
    }