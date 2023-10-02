package com.example.teamtube.Retrofit.Model

data class VideoResponse(
    val kind: String,
    val etag: String,
    val items: List<VideoItem>,
    val nextPageToken: String,
    val pageInfo: PageInfo,
)

data class VideoItem(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: Snippets,
)

data class Snippets(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    val channelTitle: String,
    val tags: List<String>,
    val categoryId: String,
    val localized: Localized,
    /*val defaultAudioLanguage: String?,*/
    val defaultLanguage: String?,
)

data class Thumbnails(
    val high: High,
)

data class High(
    val url: String,
    val width: Long,
    val height: Long,
)

data class Localized(
    val title: String,
    val description: String,
)

data class PageInfo(
    val totalResults: Long,
    val resultsPerPage: Long,
)
