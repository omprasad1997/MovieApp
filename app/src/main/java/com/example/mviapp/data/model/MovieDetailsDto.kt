package com.example.mviapp.data.model

data class MovieDetailsDto(
    val Title: String,
    val Year: String,
    val Rated: String,
    val Released: String,
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Writer: String,
    val Actors: String,
    val Plot: String,
    val Language: String,
    val Country: String,
    val Awards: String,
    val Poster: String,
    val imdbRating: String,
    val imdbID: String,
    val Response: String,
    val Error: String?
)