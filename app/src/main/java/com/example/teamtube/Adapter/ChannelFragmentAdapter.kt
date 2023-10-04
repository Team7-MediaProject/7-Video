package com.example.teamtube.ChannelData

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.Glide
import com.example.teamtube.MainActivity
import com.example.teamtube.Model.ChannelModel
import com.example.teamtube.databinding.CategoryChannelItemBinding

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

        Glide.with(mContext)
            .load(itemChannel.thumbnails)
            .into(holder.img_channel)

        holder.title_channel.text = itemChannel.title

        holder.img_like.visibility = if(itemChannel.isLike)View.VISIBLE else View.INVISIBLE
    }

    override fun getItemCount(): Int {
        return itemsChannel.size
    }

    inner class ChannelViewHolder(private val binding: CategoryChannelItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        var cl_channel : ConstraintLayout = binding.channelItemConstraintLayout
        var img_channel : ImageView = binding.imgChannel
        var title_channel : TextView = binding.titleChannel
        var img_like : ImageView = binding.likeImageView

        init {
//            img_like.visibility = View.INVISIBLE
            cl_channel.setOnClickListener(this)
            img_channel.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return
            val item = itemsChannel[position]

            item.isLike = !item.isLike

            if(item.isLike) {
                //img_like.visibility = View.VISIBLE
                (mContext as MainActivity).addLikedItem(item)
            } else {
                //img_like.visibility = View.INVISIBLE
                (mContext as MainActivity).removeLikedItem(item)
            }
            Log.d("ppp","ppp: $position, $item")
            notifyItemChanged(position)
        }
    }

    fun updateData(newItems: List<ChannelModel>) {
        itemsChannel.clear()
        itemsChannel.addAll(newItems)
        notifyDataSetChanged()
    }
}