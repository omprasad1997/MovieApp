package com.example.mviapp.ui.movie.moviedetails

import com.example.mviapp.data.model.MovieDetails

data class MovieDetailsState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetails? = null,
    val isFavourite: Boolean = false,
    val error: String? = null
)
