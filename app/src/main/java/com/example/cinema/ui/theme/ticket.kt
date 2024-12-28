package com.example.cinema.ui.theme

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "tickets")
data class Ticket(
    @PrimaryKey(autoGenerate = true) val ticketId: Int = 0,
    val movieId: Int,
    val clientId: Int,
    val seatNumber: String,
)