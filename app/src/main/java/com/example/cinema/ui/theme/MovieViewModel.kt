package com.example.cinema.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieViewModel(private val movieDao: MovieDao) : ViewModel() {


    suspend fun getAllMovie() = movieDao.getAllMovies()
    fun insert(movie: Movie) {
        viewModelScope.launch {
            movieDao.insert(movie)
        }
    }

    fun update(movie: Movie) {
        viewModelScope.launch {
            movieDao.update(movie)
        }
    }

    fun delete(movie: Movie) {
        viewModelScope.launch {
            movieDao.delete(movie)
        }
    }

}