package com.example.teamtube.Retrofit

data class Root(
    val kind: String,
    val etag: String,
    val items: List<Item>,
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
