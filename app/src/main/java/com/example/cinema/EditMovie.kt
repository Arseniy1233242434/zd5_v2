package com.example.cinema

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.ui.theme.AppDatabase
import com.example.cinema.ui.theme.Movie
import com.example.cinema.ui.theme.MovieAdapter
import com.example.cinema.ui.theme.MovieDao
import com.example.cinema.ui.theme.MovieViewModel
import com.example.cinema.ui.theme.MovieViewModelFactory
import com.example.cinema.ui.theme.UserViewModel
import com.example.cinema.ui.theme.UserViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditMovie : AppCompatActivity() {
    private lateinit var movieViewModel1: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    override fun onCreate(savedInstanceState: Bundle?)
      {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_movie)

        val movieDao = AppDatabase.getDatabase(application).movieDao()
        val factory = MovieViewModelFactory(movieDao)
        movieViewModel1 = ViewModelProvider(this, factory).get(MovieViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView1)
        recyclerView.layoutManager = LinearLayoutManager(this)
        movieAdapter = MovieAdapter(movieViewModel1,mutableListOf(), ::editMovie, ::deleteMovie)
        recyclerView.adapter = movieAdapter
          loadMovies()
       }
    private fun loadMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            val Movies = movieViewModel1.getAllMovie()
            movieAdapter.updateMovies(Movies)
        }
    }
    private fun editMovie(movie: Movie)
    {

    }

    private fun deleteMovie(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            movieViewModel1.delete(movie)
            loadMovies()
        }
    }
    }

