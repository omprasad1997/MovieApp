package com.example.mviapp.ui.movie.search

import com.example.mviapp.data.model.Movie

sealed class SearchIntent {
    data class SearchMovie(val query: String) : SearchIntent()
    data class SelectMovie(val imdbId: String) : SearchIntent()
    data class ToggleFavourite(val movie: Movie) : SearchIntent()
}
