package com.example.teamtube.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchData(
    val title: String,
    val thumbnails: String,
    val id: String,
): Parcelable{
}
