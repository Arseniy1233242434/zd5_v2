package com.example.cinema

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.cinema.ui.theme.AppDatabase
import com.example.cinema.ui.theme.Movie
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class SearchClient : AppCompatActivity() {
    private lateinit var title: TextView
    private lateinit var release: TextView
    private lateinit var genre: TextView
    private lateinit var Poster: ImageView
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_client)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun p(view: View)
    {
        searchEditText=findViewById(R.id.sss)
        val query = searchEditText.text.toString()
        if (query.isNotEmpty())
        {
            lifecycleScope.launch {
            searchMovie(query)}
        }
    }
        suspend fun searchMovie(query: String) {
            lifecycleScope.launch {
                val movies = getMoviesFromDatabase(this@SearchClient)
                val movie = movies.find { it.title.equals(query, ignoreCase = true) }

                if (movie != null) {
                    displayMovieDetails(movie)
                } else {

                }
            }
        }
        suspend fun displayMovieDetails(movie: Movie)
        {
            title=findViewById(R.id.tittle)
            title.text=movie.title
            release=findViewById(R.id.real)
            release.text=movie.releaseDate
            genre=findViewById(R.id.genre)
            genre.text=movie.genre
            Poster=findViewById(R.id.poster)
            Picasso.get()
                .load(movie.photoUrl)
                .into(Poster)
        }
         suspend fun getMoviesFromDatabase(context: Context): List<Movie> {
            val db = AppDatabase.getDatabase(context)
            return db.movieDao().getAllMovies() // Получаем список фильмов из базы данных
        }

    fun share(view: View)
    {
            val intent = Intent(this, Buying::class.java)
            startActivity(intent)
    }
}