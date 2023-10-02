package com.example.teamtube.Retrofit.Model

import com.google.gson.annotations.SerializedName

data class Root(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("etag")
    val etag: String,
    @SerializedName("items")
    val items: MutableList<Item>,
)

data class Item(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("etag")
    val etag: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("snippet")
    val snippet: Snippet,
)

data class Snippet(
    @SerializedName("title")
    val title: String,
    @SerializedName("assignable")
    val assignable: Boolean,
    @SerializedName("channelId")
    val channelId: String,
)
