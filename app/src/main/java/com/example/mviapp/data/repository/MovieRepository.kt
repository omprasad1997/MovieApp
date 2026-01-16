package com.example.mviapp.data.repository

import com.example.mviapp.data.model.Movie
import com.example.mviapp.data.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun getMovieDetails(imdbId: String): MovieDetails

    //for room
    suspend fun addToFavourites(movie: Movie)
    suspend fun removeFromFavourites(imdbId: String)
    fun getFavouriteMovies(): Flow<List<Movie>>
    fun isFavourite(imdbId: String): Flow<Boolean>
    fun getFavouriteIds(): Flow<Set<String>>
}
