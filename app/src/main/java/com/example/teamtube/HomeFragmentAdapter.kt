package com.example.teamtube

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamtube.Retrofit.Model.VideoItem

class HomeFragmentAdapter(private val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mItems = mutableListOf<VideoItem>()
    private val homeData = mutableListOf<HomeData>()
    private val TYPE_FAVORTIE = 0
    private val TYPE_CHANNEL = 1
    private val TYPE_VIDEO = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        // viewType에 따른 viewHolder 반환
        return when(viewType) {
/*            TYPE_FAVORTIE -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.most_popular_item, parent, false)
                FavoriteHolder(view)
            }
            TYPE_CHANNEL -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.category_channel_item, parent, false)
                ChannelHolder(view)
            }*/
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.category_video_item, parent, false)
                VideoHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    // viewType에 따른 데이터 binding 방식 변경
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemType = homeData[position].type
        when(itemType) {
/*            TYPE_FAVORTIE -> {
                //(holder as FavoriteHolder).bind(mItems[position])
                holder.setIsRecyclable(false)
            }
            TYPE_CHANNEL -> {
                //(holder as ChannelHolder).bind(mItems[position])
                holder.setIsRecyclable(false)
            }*/
            TYPE_VIDEO -> {
                (holder as VideoHolder).bind(mItems[position])
                holder.setIsRecyclable(false)
            }
        }
    }

/*    inner class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {
        val favorite_image = view.findViewById<ImageView>(R.id.img_mpv)

        // api 이용할  Glide로 추후 변경
        fun bind(item:HomeData) {
            favorite_image.setImageResource(item.image)
        }
    }*/

/*    inner class ChannelHolder(view: View) : RecyclerView.ViewHolder(view) {
        val channel_image = view.findViewById<ImageView>(R.id.img_channel)
        val channel_title = view.findViewById<TextView>(R.id.title_channel)
        fun bind(item : HomeData){
            // api 이용할  Glide로 추후 변경
            channel_image.setImageResource(item.image)
            channel_title.text = item.title
        }
    }*/

    inner class VideoHolder(view: View) : RecyclerView.ViewHolder(view) {
        val video_image = view.findViewById<ImageView>(R.id.home_category_video_image)
        val video_title = view.findViewById<TextView>(R.id.home_category_video_title)

        fun bind(item : VideoItem){
            // api 이용할  Glide로 추후 변경
            val videoItems = item.snippet.thumbnails
            if(videoItems != null) {
                val thumnailUrl = videoItems.high
                Glide.with(mContext)
                    .load(thumnailUrl)
                    .into(video_image)
            }
            video_title.text = item.snippet.title
        }

        // 사진 모서리 둥글게 잘라버리긔~
        init {
            video_image.clipToOutline = true
        }
    }
}