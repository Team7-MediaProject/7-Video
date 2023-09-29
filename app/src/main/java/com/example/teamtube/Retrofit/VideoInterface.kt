package com.example.teamtube.Retrofit

import com.example.teamtube.Retrofit.Model.CategoryVideo
import com.example.teamtube.Retrofit.Model.Root
import com.example.teamtube.Retrofit.Model.Video
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoInterface {

    @GET("videoCategories")
    fun getCategoryVideoInfo (
            //@QueryMap param : HashMap<String, String>
            @Query("part") part: String,
            @Query("regionCode") regionCode: String,
            @Query("key") apiKey: String
    ) : Call<CategoryVideo>

    @GET("videos")

    fun getVideoInfo (
        //@QueryMap param : HashMap<String, String>
        @Query("part") part : String,
        @Query("chart") chart : String,
        @Query("maxResults") maxResults : Int,
        @Query("videoCategoryId") videoCategoryId : String,
        @Query("regionCode") regionCode: String,
        @Query("key") apiKey : String
    ) : Call<Video>
}
