package com.example.teamtube

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.teamtube.SearchFragmentData.SearchItem
import com.example.teamtube.databinding.FragmentSearchItemBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.teamtube.SearchFragmentData.SearchData


class SearchFragmentAdapter(private val mContext: Context) : RecyclerView.Adapter<SearchFragmentAdapter.ItemViewHolder>() {

    var items = ArrayList<SearchData>()

    fun clearItem() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            FragmentSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]
        Log.d("adapter","thumbnails:${currentItem.thumbnails}, title:${currentItem.title} id:${currentItem.id}")
        Glide.with(mContext)
            .load(currentItem.thumbnails)
            .into(holder.sf_item_iv)

        holder.sf_item_tv.text = currentItem.title


    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder(binding: FragmentSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        var sf_item_iv: ImageView = binding.sfItemIv
        var sf_item_tv: TextView = binding.sfItemTv
        //var sf_item_cl: ConstraintLayout = binding.sfItemCl

//        init {
//            sf_item_iv.setOnClickListener(this)
//            sf_item_cl.setOnClickListener(this)
//        }


        override fun onClick(view: View?) {

        }
    }
}



