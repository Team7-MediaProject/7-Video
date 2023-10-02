package com.example.teamtube.ChannelData

import com.example.teamtube.data.YouTubeApiService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ChannelApi {
    private const val BASE_URL = "https://www.googleapis.com/youtube/"

    val apiService: ChannelApiService
        get() = instance.create(ChannelApiService::class.java)


    private val instance: Retrofit
        private get() {
            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
}