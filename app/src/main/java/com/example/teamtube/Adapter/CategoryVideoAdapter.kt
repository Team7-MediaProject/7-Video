package com.example.teamtube.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamtube.Model.CategoryList
import com.example.teamtube.VideoDetailActivity
import com.example.teamtube.databinding.CategoryVideoItemBinding

class CategoryVideoAdapter(private val mContext: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding: CategoryVideoItemBinding
    private val mItems = mutableListOf<CategoryList>()

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
        fun bind(item: CategoryList) {
            binding.homeCategoryVideoImage.setOnClickListener {
                val intent = Intent(mContext, VideoDetailActivity::class.java)
                intent.putExtra("Data", item)
                mContext.startActivity(intent)
            }
            binding.apply {
                homeCategoryVideoTitle.text = item.categoryTitle
            }

            Glide.with(mContext)
                .load(item.image)
                .into(binding.homeCategoryVideoImage)
        }
    }

    // recyclerview 갱신 시 필요
    fun setVideoItems(videoItem: List<CategoryList>) {
        mItems.clear()
        mItems.addAll(videoItem)
        Log.d("videoItems", "$mItems")
        notifyDataSetChanged()
    }
}