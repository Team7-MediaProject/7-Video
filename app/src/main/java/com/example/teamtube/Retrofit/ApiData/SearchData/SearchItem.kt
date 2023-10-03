package com.example.teamtube.Retrofit.ApiData.SearchData

data class SearchItem(
    val kind: String,
    val etag: String,
    val nextPageToken: String,
    val regionCode: String,
    val pageInfo: PageInfo,
    val items: List<Item>,
)

data class PageInfo(
    val totalResults: Long,
    val resultsPerPage: Long,
)

data class Item(
    val kind: String,
    val etag: String,
    val id: Id,
    val snippet: Snippet,
)

data class Id(
    val kind: String,
    val videoId: String,
)

data class Snippet(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    val channelTitle: String,
    val liveBroadcastContent: String,
    val publishTime: String,
)

data class Thumbnails(
    val default: Default,
    val medium: Medium,
    val high: High,
)

data class Default(
    val url: String,
    val width: Long,
    val height: Long,
)

data class Medium(
    val url: String,
    val width: Long,
    val height: Long,
)

data class High(
    val url: String,
    val width: Long,
    val height: Long,
)

