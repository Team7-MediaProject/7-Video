package com.example.teamtube.SearchFragmentData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface INetworkService {
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