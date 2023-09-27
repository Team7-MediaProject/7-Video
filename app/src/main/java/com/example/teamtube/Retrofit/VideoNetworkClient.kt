package com.example.teamtube.Retrofit

import com.example.teamtube.Constrant.Constrants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.net.NetworkInterface

object VideoNetworkClient {
    val apiService: NetworkInterface
        get() = retrofit.create(NetworkInterface::class.java)

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constrants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}