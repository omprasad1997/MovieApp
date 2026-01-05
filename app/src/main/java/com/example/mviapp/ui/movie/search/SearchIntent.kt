package com.example.mviapp.ui.movie.search

sealed class SearchIntent {
    data class SearchMovie(val query: String) : SearchIntent()
    data class SelectMovie(val imdbId: String) : SearchIntent()
}
