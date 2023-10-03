package com.example.teamtube.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamtube.databinding.MostPopularItemBinding
import com.example.teamtube.model.HomeitemModel

class HomeFragmentAdapter(private val context: Context) :
    RecyclerView.Adapter<HomeFragmentAdapter.VideoViewHolder>() {

    private val items: MutableList<HomeitemModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = MostPopularItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class VideoViewHolder(private val binding: MostPopularItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeitemModel) {
            binding.scInfo.text = item.title
            Log.d("test","item"+item.title)

            Glide.with(context)
                .load(item.thumbnails)
                .into(binding.imgMpv)
        }
    }

    fun updateData(newItems: List<HomeitemModel>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}