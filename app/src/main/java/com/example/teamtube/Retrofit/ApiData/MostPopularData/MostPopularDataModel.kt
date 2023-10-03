package com.example.teamtube.Retrofit.ApiData.MostPopularData

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Root(
    @SerializedName("kind") val kind: String,
    @SerializedName("etag") val etag: String,
    @SerializedName("items")val items: List<Item>,
    @SerializedName("nextPageToken")val nextPageToken: String,
    @SerializedName("pageInfo") @Expose val pageInfo: PageInfo,
)

data class Item(
    @SerializedName("kind")val kind: String,
    @SerializedName("etag")val etag: String,
    @SerializedName("id")val id: String,
    @SerializedName("snippet")val snippet: Snippet,
)

data class Snippet(
    @SerializedName("publishedAt")val publishedAt: String,
    @SerializedName("channelId")val channelId: String,
    @SerializedName("title")val title: String,
    @SerializedName("description")val description: String,
    @SerializedName("thumbnails")val thumbnails: Thumbnails,
    @SerializedName("channelTitle")val channelTitle: String,
    @SerializedName("tags")val tags: List<String>?,
    @SerializedName("categoryId")val categoryId: String,
    @SerializedName("liveBroadcastContent")val liveBroadcastContent: String,
    @SerializedName("localized")val localized: Localized,
    @SerializedName("defaultAudioLanguage")val defaultAudioLanguage: String?,
    @SerializedName("defaultLanguage")val defaultLanguage: String?,
)

data class Thumbnails(
    @SerializedName("default")val default: Default,
    @SerializedName("medium")val medium: Medium,
    @SerializedName("high")val high: High,
    @SerializedName("standard")val standard: Standard,
    @SerializedName("maxres")val maxres: Maxres,
)

data class Default(
    @SerializedName("url")val url: String,
    @SerializedName("width")val width: Long,
    @SerializedName("height")val height: Long,
)

data class Medium(
    @SerializedName("url")val url: String,
    @SerializedName("width")val width: Long,
    @SerializedName("height")val height: Long,
)

data class High(
    @SerializedName("url")val url: String,
    @SerializedName("width")val width: Long,
    @SerializedName("height")val height: Long,
)

data class Standard(
    @SerializedName("url")val url: String,
    @SerializedName("width")val width: Long,
    @SerializedName("height")val height: Long,
)

data class Maxres(
    @SerializedName("url")val url: String,
    @SerializedName("width")val width: Long,
    @SerializedName("height")val height: Long,
)

data class Localized(
    @SerializedName("title")val title: String,
    @SerializedName("description")val description: String,
)

data class PageInfo(
    @SerializedName("totalResults") @Expose val totalResults: Int,
    @SerializedName("resultsPerPage")@Expose val resultsPerPage: Int,
)

