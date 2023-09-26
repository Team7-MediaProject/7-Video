package com.example.teamtube.Retrofit.Model

data class VideoResponse(
    val kind : String,
    val etag : String,
    val items : MutableList<VideoItem>
)

data class VideoItem(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: Snippet
)
