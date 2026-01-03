package com.example.mviapp.data.repository

import com.example.mviapp.data.model.Movie
import com.example.mviapp.data.model.MovieDetails

interface MovieRepository {
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun getMovieDetails(imdbId: String): MovieDetails
}
