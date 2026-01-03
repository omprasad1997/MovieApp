package com.example.mviapp.mvi

import com.example.mviapp.data.model.Movie

sealed class UserIntent {
    data class SearchMovie(val query: String) : UserIntent()
    data class SelectMovie(val imdbId: String) : UserIntent()
    object LoadMovieDetails : UserIntent()
    object BackClicked : UserIntent()
}
