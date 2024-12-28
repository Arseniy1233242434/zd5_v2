package com.example.cinema.ui.theme

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true) val movieId: Int = 0,
    val title: String,
    val genre: String,
    val photoUrl: String,
    val releaseDate: String,
    val start: String?,
    val finish: String?,
    val data:String?
)