package com.example.teamtube.Retrofit

data class SearchVideoResponse(
    val kind: String,
    val etag: String,
    val videos: List<Video>
)

data class Video(
    val id: String,
    val kind: String,
    val etag : String,
    val snippet : Snippet,
    val statistics : Statistics
)

data class Snippet(
    val publishedAt : String,
    val channelId : String,
    val title : String,
    val description : String,
    val thumbnails : Thumbnails,
    val categoryId : String
)

data class Thumbnails (
    val default : Thumbnail,
    val medium : Thumbnail,
    val high : Thumbnail
)

data class Thumbnail(
    val url : String
)


data class Statistics (
    val viewCount : String,
    val likeCount : String,
    // val dislikeCount : String,
    val favoriteCount : String,
    val commentCount : String
)