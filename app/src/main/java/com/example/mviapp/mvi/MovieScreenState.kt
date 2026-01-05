package com.example.mviapp.mvi

import com.example.mviapp.data.model.Movie
import com.example.mviapp.data.model.MovieDetails

data class MovieScreenState(
    val isSearchLoading: Boolean = false,
    val isDetailsLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val movieDetails: MovieDetails? = null,
    val selectedImdbId: String? = null,
    val error: String? = null
)

//need 2 seperate state & viewmodel
//Movie list state -
//Movie Details state