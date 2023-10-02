package com.example.teamtube.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {
    @GET("v3/videos")
    fun listVideos(
        @Query("regionCode") regionCode: String,
        @Query("part") part: String = "snippet",
        @Query("chart") chart: String = "mostPopular",
        @Query("maxResults") maxResults: Int = 10,
        @Query("key") apikey: String
    ): Call<Root>
}
