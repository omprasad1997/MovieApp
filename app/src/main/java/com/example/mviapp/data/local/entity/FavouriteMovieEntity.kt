package com.example.mviapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_movies")
data class FavouriteMovieEntity(
    @PrimaryKey val imdbId: String,
    val title: String,
    val year: String,
    val poster: String
)
