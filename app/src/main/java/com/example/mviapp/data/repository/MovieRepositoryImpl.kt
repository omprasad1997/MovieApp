package com.example.mviapp.data.repository

import com.example.mviapp.data.local.dao.FavouriteMovieDao
import com.example.mviapp.data.local.entity.FavouriteMovieEntity
import com.example.mviapp.data.model.Movie
import com.example.mviapp.data.model.MovieDetails
import com.example.mviapp.data.remote.OmdbApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: OmdbApiService,
    private val favouriteDao: FavouriteMovieDao
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
                rating = response.imdbRating,
                rated = response.Rated,
                released = response.Released,
                writer = response.Writer,
                actors = response.Actors,
                language = response.Language,
                country = response.Country,
                awards = response.Awards,
                imdbID = response.imdbID
            )
        } else {
            throw Exception(response.Error ?: "Failed to load movie details")
        }
    }

    override suspend fun addToFavourites(movie: Movie) {
        favouriteDao.insert(
            FavouriteMovieEntity(
                imdbId = movie.id,
                title = movie.title,
                year = movie.year,
                poster = movie.poster
            )
        )
    }

    override suspend fun removeFromFavourites(imdbId: String) {
        favouriteDao.delete(imdbId)
    }

    override fun getFavouriteMovies(): Flow<List<Movie>> =
        favouriteDao.getAllFavourites().map { list ->
            list.map {
                Movie(
                    id = it.imdbId,
                    title = it.title,
                    year = it.year,
                    poster = it.poster
                )
            }
        }

    override fun isFavourite(imdbId: String): Flow<Boolean> =
        favouriteDao.isFavourite(imdbId)

    override fun getFavouriteIds(): Flow<Set<String>> {
        return favouriteDao
            .getAllFavouriteIds()      // Flow<List<String>>
            .map { it.toSet() }
    }

}



