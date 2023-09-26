package com.example.teamtube.Retrofit

import com.example.teamtube.Retrofit.Model.Root
import retrofit2.http.GET
import retrofit2.http.QueryMap

// MostPopular interface
interface PopularInterface {
}

// Category-Channel interface
interface ChannelInterface {
}

// Category-Video interface
interface VideoInterface {
    @GET("videoCategories")
    suspend fun getVideoInfo(
        @QueryMap param : HashMap<String, String>
        /*@Query("part") part: String,
        @Query("regionCode") regionCode: String,
        @Query("Key") apiKey: String*/
    ) : Root
}

interface VideoImageInterface {
    @GET("videos")
    suspend fun getVideoImageInfo(
        @QueryMap param : HashMap<String, String>
    )
}