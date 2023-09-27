package com.example.teamtube.Retrofit

import com.example.teamtube.Retrofit.Model.Root
import com.example.teamtube.Retrofit.Model.Video
import retrofit2.http.GET
import retrofit2.http.Query

// MostPopular interface
interface PopularInterface {
}

// Category-Channel interface
interface ChannelInterface {
}

// Category-Video interface
interface VideoInterface {
    @GET("videoCategories")
    suspend fun getCategoryVideoInfo(
        //@QueryMap param : HashMap<String, String>
        @Query("part") part: String,
        @Query("regionCode") regionCode: String,
        @Query("key") apiKey: String
    ) : Root
}

interface VideoImageInterface {
    @GET("videos")
    suspend fun getVideoInfo(
        //@QueryMap param : HashMap<String, String>
        @Query("part") part : String,
        @Query("chart") chart : String,
        @Query("maxResults") maxResults : Int,
        @Query("videoCategoryId") videoCategoryId : String,
        @Query("key") apiKey : String
    ) : Video
}