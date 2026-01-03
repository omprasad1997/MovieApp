package com.example.mviapp.data.model

data class MovieSearchResponse(
    val Search: List<MovieDto>?,
    val totalResults: String?,
    val Response: String,
    val Error: String?
)
