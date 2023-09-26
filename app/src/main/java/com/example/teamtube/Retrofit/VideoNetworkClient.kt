package com.example.teamtube.Retrofit

import com.example.teamtube.Constrant.Constrants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object VideoNetworkClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constrants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // 인기있는 영상 retrofit
    val popularService = retrofit.create(PopularInterface::class.java)
    // Category-Channel retrofit
    val channelService = retrofit.create(ChannelInterface::class.java)
    // Category-Video retrofit
    val videoService = retrofit.create(VideoInterface::class.java)
    // VideoImage retrofit - 썸네일
    val imageService = retrofit.create(VideoImageInterface::class.java)
}