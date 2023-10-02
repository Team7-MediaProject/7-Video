package com.example.teamtube.CategoryVideoData

import com.example.teamtube.CategoryVideoData.Model.Root
import com.example.teamtube.CategoryVideoData.Model.VideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoInterface {

    @GET("v3/videoCategories")
    fun getCategoryVideoInfo (
            //@QueryMap param : HashMap<String, String>
            @Query("part") part: String,
            @Query("regionCode") regionCode: String,
            @Query("key") apiKey: String
    ) : Call<Root>

    @GET("v3/videos")

    fun getVideoInfo (
        //@QueryMap param : HashMap<String, String>
        @Query("part") part : String,
        @Query("chart") chart : String,
        @Query("maxResults") maxResults : Int,
        @Query("videoCategoryId") videoCategoryId : String,
        @Query("regionCode") regionCode: String,
        @Query("key") apiKey : String
    ) : Call<VideoResponse>
}
