package com.example.cinema

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.ui.theme.AppDatabase
import com.example.cinema.ui.theme.Movie
import com.example.cinema.ui.theme.MovieAdapter
import com.example.cinema.ui.theme.TicketAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Buying : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var ticketAdapter: TicketAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var b: Button
    private var movies1: MutableList<Movie> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buying)
        recyclerView = findViewById(R.id.recyclerbuy)
        recyclerView.layoutManager = LinearLayoutManager(this)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val movies = getMoviesFromDatabase() // Реализуйте этот метод для получения данных
        ticketAdapter = TicketAdapter(movies1, sharedPreferences)
        recyclerView.adapter = ticketAdapter



    }

    private fun getMoviesFromDatabase() {
        val db = AppDatabase.getDatabase(this)

        // Запускаем корутину для получения данных из базы данных
        CoroutineScope(Dispatchers.IO).launch {

            val moviesFromDb = db.movieDao().getAllMovies()
            withContext(Dispatchers.Main) {
                updateMovies(moviesFromDb) // Обновляем адаптер с новыми данными
            }
        }
    }
    fun getItemCount(): Int = movies1.size
    private fun updateMovies(newMovies: List<Movie>) {
        movies1.clear() // Очищаем текущий список
        movies1.addAll(newMovies) // Добавляем новые фильмы
        ticketAdapter.notifyDataSetChanged() // Уведомляем адаптер об изменениях
    }
}