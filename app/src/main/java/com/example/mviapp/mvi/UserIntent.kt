package com.example.mviapp.mvi

import com.example.mviapp.data.model.Movie

sealed class UserIntent {
    data class SelectUser(val id: Int, val name: String) : UserIntent()
    object BackClicked : UserIntent()
    data class SearchMovie(val query: String) : UserIntent()
    data class SelectMovie(val movie: Movie) : UserIntent()
}
