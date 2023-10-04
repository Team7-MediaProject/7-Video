package com.example.teamtube.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeitemModel (
    val id: String,
    var title: String,
    var thumbnails: String,
): Parcelable{
}
