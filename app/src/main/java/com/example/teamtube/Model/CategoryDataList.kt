package com.example.teamtube.Model

// api 실제 데이터 리스트
class CategoryDataList {
    val categoryList: List<com.example.teamtube.Retrofit.ApiData.CategoryVideoData.CategoryList> = listOf(
        com.example.teamtube.Retrofit.ApiData.CategoryVideoData.CategoryList(
            "1",
            "Film & Animation"
        ),
        com.example.teamtube.Retrofit.ApiData.CategoryVideoData.CategoryList(
            "2",
            "Autos & Vehicles"
        ),
        com.example.teamtube.Retrofit.ApiData.CategoryVideoData.CategoryList("10", "Music"),
        com.example.teamtube.Retrofit.ApiData.CategoryVideoData.CategoryList(
            "15",
            "Pets & Animals"
        ),
        com.example.teamtube.Retrofit.ApiData.CategoryVideoData.CategoryList("17", "Sports"),
        com.example.teamtube.Retrofit.ApiData.CategoryVideoData.CategoryList("20", "Gaming"),
    )

}