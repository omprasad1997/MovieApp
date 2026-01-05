package com.example.mviapp.mvi

sealed class MovieScreenIntent {
    data class SearchMovie(val query: String) : MovieScreenIntent()
    data class SelectMovie(val imdbId: String) : MovieScreenIntent()
    object LoadMovieDetails : MovieScreenIntent()
    object BackClicked : MovieScreenIntent()
}
