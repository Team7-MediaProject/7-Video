package com.example.teamtube.ChannelData

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamtube.databinding.CategoryChannelItemBinding
import com.example.teamtube.model.HomeitemModel

class ChannelFragmentAdapter (private val mContext: Context) :
    RecyclerView.Adapter<ChannelFragmentAdapter.ChannelViewHolder>() {

    var itemsChannel = ArrayList<ChannelModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val binding = CategoryChannelItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChannelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val itemChannel = itemsChannel[position]
        holder.bindChannel(itemChannel)
    }

    override fun getItemCount(): Int {
        return itemsChannel.size
    }

    inner class ChannelViewHolder(private val binding: CategoryChannelItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindChannel(item: ChannelModel) {
            val img_channel : ImageView = binding.imgChannel
            val title_channel : TextView = binding.titleChannel

            Glide.with(mContext)
                .load(item.thumbnails)
                .into(img_channel)

            title_channel.text = item.title
        }
    }

    fun updateData(newItems: List<ChannelModel>) {
        itemsChannel.clear()
        itemsChannel.addAll(newItems)
        notifyDataSetChanged()
    }
}