package com.example.cinema.ui.theme

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey(autoGenerate = true) val genreId: Int = 0,
    val genreName: String
)