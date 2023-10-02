package com.example.teamtube.MostPopularData


import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object YouTubeApi {
    private const val BASE_URL = "https://www.googleapis.com/youtube/"

    val apiService: YouTubeApiService
        get() = instance.create(YouTubeApiService::class.java)


    private val instance: Retrofit
        private get() {
            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
}