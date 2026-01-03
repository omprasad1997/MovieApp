package com.example.mviapp.data.repository

import com.example.mviapp.data.model.Movie

interface MovieRepository {
    suspend fun searchMovies(query: String): List<Movie>
}
