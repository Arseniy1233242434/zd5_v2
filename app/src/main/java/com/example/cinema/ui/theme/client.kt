package com.example.cinema.ui.theme

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
data class Client(
    @PrimaryKey(autoGenerate = true) val clientId: Int = 0,
    var password: String,
    var email: String,
    var date: String,
    var role:String
)