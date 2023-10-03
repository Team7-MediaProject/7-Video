package com.example.teamtube.Retrofit.retrofit

import com.example.teamtube.Retrofit.ApiModel.Root
import com.example.teamtube.Retrofit.ApiModel.VideoResponse
import com.example.teamtube.SearchFragmentData.SearchItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoInterface {
    @GET("v3/videos")
    fun listVideos(
        @Query("regionCode") regionCode: String,
        @Query("part") part: String = "snippet",
        @Query("chart") chart: String = "mostPopular",
        @Query("maxResults") maxResults: Int = 10,
        @Query("key") apikey: String
    ): Call<com.example.teamtube.MostPopularData.Root>

    @GET("v3/channels")
    fun listChannels(
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: Int,
        @Query("key") apikey: String,
        @Query("id") id: String
    ): Call<com.example.teamtube.MostPopularData.Root>

    @GET("v3/videoCategories")
    fun getCategoryVideoInfo(
        //@QueryMap param : HashMap<String, String>
        @Query("part") part: String,
        @Query("regionCode") regionCode: String,
        @Query("key") apiKey: String
    ): Call<Root>

    @GET("v3/videos")
    fun getVideoInfo(
        //@QueryMap param : HashMap<String, String>
        @Query("part") part: String,
        @Query("chart") chart: String,
        @Query("maxResults") maxResults: Int,
        @Query("videoCategoryId") videoCategoryId: String,
        @Query("regionCode") regionCode: String,
        @Query("key") apiKey: String
    ): Call<VideoResponse>

    @GET("v3/search")
    fun videoSearch(
        @Query("key") apiKey: String?,
        @Query("part") part: String?,
        @Query("maxResults") maxResults: Int?,
        @Query("q") q: String?,
        @Query("regionCode") regionCode: String?,
        @Query("type") type: String?,
    ): Call<SearchItem?>

}
