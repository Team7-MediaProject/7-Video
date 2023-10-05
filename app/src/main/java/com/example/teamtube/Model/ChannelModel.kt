package com.example.teamtube.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChannelModel (
    val thumbnails: String?,
    val id: String?,
    val title: String?,
    var isLike: Boolean = false
) : Parcelable