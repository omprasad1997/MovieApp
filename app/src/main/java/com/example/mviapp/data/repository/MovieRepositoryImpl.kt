package com.example.mviapp.data.repository

import com.example.mviapp.data.model.Movie
import com.example.mviapp.data.model.MovieDetails
import com.example.mviapp.data.remote.OmdbApiService
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: OmdbApiService
) : MovieRepository {

    override suspend fun searchMovies(query: String): List<Movie> {
        val response = api.searchMovies(query)

        if (response.Response == "True") {
            return response.Search?.map {
                Movie(
                    id = it.imdbID,
                    title = it.Title,
                    year = it.Year,
                    poster = it.Poster
                )
            } ?: emptyList()
        } else if (response.Error == "Too many results.") {
            return emptyList()
        } else {
            throw Exception(response.Error ?: "Unknown error")
        }
    }

    override suspend fun getMovieDetails(imdbId: String): MovieDetails {
        val response = api.getMovieDetails(imdbId)

        if (response.Response == "True") {
            return MovieDetails(
                title = response.Title,
                year = response.Year,
                runtime = response.Runtime,
                genre = response.Genre,
                director = response.Director,
                plot = response.Plot,
                poster = response.Poster,
                rating = response.imdbRating
            )
        } else {
            throw Exception(response.Error ?: "Failed to load movie details")
        }
    }

}
