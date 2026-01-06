package com.example.mviapp.ui.movie.home

import com.example.mviapp.data.model.Movie

sealed class HomeIntent {
    object LoadTrending : HomeIntent()
    data class SelectMovie(val movie: Movie) : HomeIntent()
    object ClearError : HomeIntent()
}
