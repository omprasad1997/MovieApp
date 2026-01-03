package com.example.mviapp.data.repository

import com.example.mviapp.data.model.Movie
import com.example.mviapp.data.remote.OmdbApi
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: OmdbApi
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
        } else {
            throw Exception(response.Error ?: "Unknown error")
        }
    }
}
