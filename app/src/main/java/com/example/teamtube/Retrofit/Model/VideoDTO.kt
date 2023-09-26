package com.example.teamtube.Retrofit.Model

data class Root(
    val kind: String,
    val etag: String,
    val items: MutableList<Item>,
)

data class Item(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: Snippet,
)

data class Snippet(
    val title: String,
    val assignable: Boolean,
    val channelId: String,
)
