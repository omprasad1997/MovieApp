package com.example.mviapp.ui.movie.home

import com.example.mviapp.data.model.Movie

sealed class HomeIntent {
    object LoadTrending : HomeIntent()
    data class SelectMovie(val movie: Movie) : HomeIntent()
    data class ToggleFavourite(val movie: Movie) : HomeIntent() // ✅ ADD
    object ClearError : HomeIntent()
}
