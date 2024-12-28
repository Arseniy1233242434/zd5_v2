package com.example.cinema.ui.theme

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MovieDao {
    @Insert
    suspend fun insert(movie: Movie)
    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<Movie>
    @Delete
    suspend fun delete(client: Movie)
    @Update
    suspend fun update(client: Movie)
    @Query("SELECT * FROM movies WHERE title = :title")
    suspend fun getMoviesByTitle(title: String): List<Movie>
}