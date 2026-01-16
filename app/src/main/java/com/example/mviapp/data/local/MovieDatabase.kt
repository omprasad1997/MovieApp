package com.example.mviapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mviapp.data.local.dao.FavouriteMovieDao
import com.example.mviapp.data.local.entity.FavouriteMovieEntity

@Database(
    entities = [FavouriteMovieEntity::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun favouriteMovieDao(): FavouriteMovieDao
}
