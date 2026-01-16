package com.example.mviapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mviapp.data.local.entity.FavouriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: FavouriteMovieEntity)

    @Query("DELETE FROM favourite_movies WHERE imdbId = :imdbId")
    suspend fun delete(imdbId: String)

    @Query("SELECT * FROM favourite_movies")
    fun getAllFavourites(): Flow<List<FavouriteMovieEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_movies WHERE imdbId = :imdbId)")
    fun isFavourite(imdbId: String): Flow<Boolean>

    @Query("SELECT imdbId FROM favourite_movies")
    fun getAllFavouriteIds(): Flow<List<String>>

}
