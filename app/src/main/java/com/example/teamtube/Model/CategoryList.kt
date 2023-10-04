package com.example.teamtube.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryList(
    val id : String,
    val categoryTitle : String,
    val image : String,
    val description: String
): Parcelable{
}

