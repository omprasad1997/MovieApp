package com.example.mviapp.ui.movie.moviedetails

sealed class MovieDetailsIntent {
    object LoadMovieDetails : MovieDetailsIntent()
    object BackClicked : MovieDetailsIntent()
}
