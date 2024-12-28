package com.example.cinema

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R.*
import com.example.cinema.ui.theme.AppDatabase
import com.example.cinema.ui.theme.GenreAdapter
import com.example.cinema.ui.theme.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar


class KlientScreen : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var search:Button
    private lateinit var movies:List<Movie>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_klient_screen)


        recyclerView = findViewById(R.id.recyclerVieww)
        recyclerView.layoutManager = LinearLayoutManager(this@KlientScreen)

        lifecycleScope.launch {
            try {
                movies = getMoviesFromDatabase(this@KlientScreen)
                val movies1 = movies.distinctBy { it.title }
                Log.d("KlientScreen", "Fetched movies: ${movies.size}")
                val groupedMovies = movies1.groupBy { it.genre }
                genreAdapter = GenreAdapter(groupedMovies)
                recyclerView.adapter = genreAdapter

            } catch (e: Exception) {
                // Обработка ошибок, например, вывод сообщения об ошибке
                Log.e("KlientScreen", "Error fetching movies", e)
            }

        }


        search=findViewById(id.searched)
        search.setOnClickListener {

            val intent = Intent(this, SearchClient::class.java)
            startActivity(intent)
        }

    }
    private suspend fun getMoviesFromDatabase(context: Context): List<Movie> {
        val db = AppDatabase.getDatabase(context)

        return db.movieDao().getAllMovies() // Получаем список фильмов из базы данных
    }

}