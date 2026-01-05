package com.example.mviapp.ui.movie.search

sealed class SearchEffect {
    data class NavigateToMovieDetails(val imdbId: String) : SearchEffect()
}
