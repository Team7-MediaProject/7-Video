package com.example.teamtube.ChannelData

import com.example.teamtube.MostPopularData.Root
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ChannelApiService {
    @GET("v3/channels")
    fun listChannels(
        @Query("part") part: String = "snippet",
        @Query("maxResults")maxResults: Int,
        @Query("key") apikey: String,
        @Query("id") id: String
    ) : Call<Root>
}