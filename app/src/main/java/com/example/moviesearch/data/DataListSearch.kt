package com.example.moviesearch.data

data class DataListSearch(
    val Search: List<DataList>
)

data class DataList(
    val Title: String?,
    val Year: String?,
    val imdbID: String?,
    val Type: String?,
    val Poster: String?
)
