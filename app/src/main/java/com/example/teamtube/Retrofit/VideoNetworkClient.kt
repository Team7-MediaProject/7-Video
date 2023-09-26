package com.example.teamtube.Retrofit

import com.example.teamtube.Constrant.Constrants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VideoNetworkClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constrants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(VideoNetworkInterface::class.java)
}