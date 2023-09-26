package com.example.teamtube.Retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

// MostPopular interface
interface VideoNetworkInterface {
}

// Category-Channel interface
interface ChannelInterface {
}

// Category-Video interface
interface VideoInterface {
    @GET("videoCategories")
    suspend fun getCategoryInfo(
        @QueryMap param : HashMap<String, String>
        /*@Query("part") part: String,
        @Query("regionCode") regionCode: String,
        @Query("Key") apiKey: String*/
    ) : Root
}