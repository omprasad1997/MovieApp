package com.example.mviapp.ui.movie.home

sealed class HomeEffect {
    data class NavigateToMovieDetails(val imdbId: String) : HomeEffect()
}