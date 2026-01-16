package com.example.mviapp.ui.movie.home

sealed class HomeEffect {
    data class ShowSnackbar(val message: String) : HomeEffect()
    data class NavigateToMovieDetails(val imdbId: String) : HomeEffect()
}