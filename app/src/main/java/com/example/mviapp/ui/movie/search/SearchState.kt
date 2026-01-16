package com.example.mviapp.ui.movie.search

import com.example.mviapp.data.model.Movie

data class SearchState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String? = null,
    val favouriteIds: Set<String> = emptySet()
)
