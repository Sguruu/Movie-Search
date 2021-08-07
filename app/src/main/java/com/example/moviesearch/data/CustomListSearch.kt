package com.example.moviesearch.data

data class CustomListSearch(
    val Search: List<CustomSearch>
)

data class CustomSearch(
    val Title: String?,
    val Year: String?,
    val imdbID: String?,
    val Type: String?,
    val Poster: String?,
    var flag: Boolean = false
)

