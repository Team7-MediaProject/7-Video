package com.example.teamtube.Retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NetworkInterface {
    @GET("videos")
    suspend fun getVideoInfo(
        //@QueryMap param : HashMap<String, String>
        @Query("part") part : String,
        @Query("id") id : String,
    ): SearchVideoResponse
}