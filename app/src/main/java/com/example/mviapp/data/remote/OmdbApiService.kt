package com.example.mviapp.data.remote

import com.example.mviapp.data.model.MovieDetailsDto
import com.example.mviapp.data.model.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {

    @GET("/")
    suspend fun searchMovies(
        @Query("s") query: String,
        @Query("apikey") apiKey: String = ApiConstants.API_KEY
    ): MovieSearchResponse

    @GET("/")
    suspend fun getMovieDetails(
        @Query("i") imdbId: String,
        @Query("apikey") apiKey: String = ApiConstants.API_KEY
    ): MovieDetailsDto

}