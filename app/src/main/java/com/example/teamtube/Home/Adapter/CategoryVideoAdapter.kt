package com.example.teamtube.Home.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamtube.Retrofit.Model.Item
import com.example.teamtube.databinding.CategoryVideoItemBinding

class CategoryVideoAdapter(private val mContext: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding: CategoryVideoItemBinding
    private val mItems = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = CategoryVideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoryVideoHolder(binding)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mItems[position]
        (holder as CategoryVideoHolder).bind(item)
    }

    inner class CategoryVideoHolder(private val binding: CategoryVideoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.homeCategoryVideoTitle.text = item.snippet.title

/*            Glide.with(mContext)
                .load(item.videoCategorySnippet)
                .into(binding.homeCategoryVideoImage)*/

            /*val videoItems = item.snippet.thumbnails
            if (videoItems != null) {
                val thumnailUrl = videoItems.high
                Glide.with(mContext)
                    .load(thumnailUrl)
                    .into(video_image)
            }*/
        }

    }
    fun setVideoItems(videoItem: List<Item>) {
        mItems.clear()
        mItems.addAll(videoItem)
        notifyDataSetChanged()
    }
}