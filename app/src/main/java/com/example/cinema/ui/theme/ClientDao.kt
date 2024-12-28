package com.example.cinema.ui.theme

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ClientDao {
    @Insert
    suspend fun insert(client: Client)

    @Query("SELECT * FROM clients")

    suspend fun getAllClients(): List<Client>
    @Query("SELECT * FROM clients WHERE email = :username AND password = :password LIMIT 1")
    suspend fun getUserByCredentials(username: String, password: String): Client?
    @Delete
    suspend fun delete(client: Client)
    @Update
    suspend fun update(client: Client)
}