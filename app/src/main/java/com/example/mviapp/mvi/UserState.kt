package com.example.mviapp.mvi

import com.example.mviapp.data.model.Movie
import com.example.mviapp.data.model.User

data class UserState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val selectedMovie: Movie? = null,
    val error: String? = null
)
