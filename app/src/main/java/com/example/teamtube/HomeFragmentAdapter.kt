package com.example.teamtube

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HomeFragmentAdapter(private val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_FAVORTIE = 0
    private val TYPE_CHANNEL = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        // viewType에 따른 viewHolder 반환
        return when(viewType) {
            TYPE_FAVORTIE -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.most_popular_item, parent, false)
                FavoriteHolder(view)
            }
            TYPE_CHANNEL -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.category_channel_item, parent, false)
                ChannelHolder(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.category_video_item, parent, false)
                VideoHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    inner class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {
        val favorite_image = view.findViewById<ImageView>(R.id.img_mpv)
    }

    inner class ChannelHolder(view: View) : RecyclerView.ViewHolder(view) {
        val channel_image = view.findViewById<ImageView>(R.id.img_channel)
    }

    inner class VideoHolder(view: View) : RecyclerView.ViewHolder(view) {
        val video_image = view.findViewById<ImageView>(R.id.home_category_video_image)
        val video_title = view.findViewById<TextView>(R.id.home_category_video_title)

        // 사진 모서리 둥글게 잘라버리긔~
        init {
            video_image.clipToOutline = true
        }
    }
/*    fun bind(item) {

    }*/
}