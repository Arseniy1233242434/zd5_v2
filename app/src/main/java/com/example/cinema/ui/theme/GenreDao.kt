package com.example.cinema.ui.theme

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GenreDao {
    @Insert
    suspend fun insert(genre: Genre)

    @Query("SELECT * FROM genres")
    suspend fun getAllGenres(): List<Genre>
}