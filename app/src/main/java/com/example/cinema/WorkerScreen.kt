package com.example.cinema

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import com.example.cinema.ui.theme.Client
import com.example.cinema.ui.theme.Movie
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class WorkerScreen : AppCompatActivity() {
    lateinit var a:EditText
    lateinit var result:Movie
    lateinit var name:TextView
    lateinit var genre:TextView
    lateinit var date:TextView
    lateinit var image:ImageView
    lateinit var DateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_worker_screen)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    suspend fun searchMovieByName(movieName: String): Movie? {
        val apiKey = "8424b5c9"
        val url = "https://www.omdbapi.com/?t=${movieName.replace(" ", "+")}&apikey=$apiKey"

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        return withContext(Dispatchers.IO) { // Выполняем сетевой запрос в фоновом потоке
            try {
                val response: Response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val jsonResponse = response.body?.string()
                    jsonResponse?.let {
                        val jsonObject = JSONObject(it)
                        if (jsonObject.getString("Response") == "True") {
                            // Извлекаем данные о фильме
                            val title = jsonObject.getString("Title")
                            val genre = jsonObject.getString("Genre") // Получаем жанр
                            val releaseDate = jsonObject.getString("Released")
                            val posterPath = jsonObject.getString("Poster")
                            val genre1=genre.split(",").firstOrNull()?.trim()
                            // Возвращаем объект Movie
                            if (genre1 != null) {
                                Movie(title = title, genre = genre1, photoUrl = posterPath, releaseDate = releaseDate, start = null, finish = null, data = null)
                            }
                            else
                                null
                        } else {
                            null // Фильм не найден
                        }
                    }
                } else {
                    println("Ошибка: ${response.code} - ${response.message}")
                    null // Ошибка при выполнении запроса
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null // Обработка исключений
            }
        }
    }
    fun addMovieToDatabase(movie: Movie) {
        val t=findViewById<TextView>(R.id.start)
        val t1=findViewById<TextView>(R.id.finish)
        val f=Movie(movieId = result.movieId, title = result.title,genre=result.genre, photoUrl = result.photoUrl, releaseDate = result.releaseDate, start =t.text.toString(), finish = t1.text.toString(), data =DateButton.text.toString()  )
        val movieDao = AppDatabase.getDatabase(applicationContext).movieDao()
        CoroutineScope(Dispatchers.IO).launch {
            movieDao.insert(f)
        }
    }

    fun search(view: View)
    {
        a=findViewById(R.id.a)
        CoroutineScope(Dispatchers.Main).launch{
        val b=searchMovieByName(a.text.toString())
        if(b!=null)
        {
            result=b
            name=findViewById(R.id.name)
            genre=findViewById(R.id.genre)
            date=findViewById(R.id.date)
            image=findViewById(R.id.image)
            name.text=b.title
            genre.text=b.genre
            date.text=b.releaseDate
            Picasso.get()
                .load(b.photoUrl) // Замените на ваш ресурс-заполнитель // Замените на ваш ресурс ошибки
                .into(image)
        }

        }


    }

    fun load(view: View)
    {
        addMovieToDatabase(result)
    }

    fun datepicker1(view: View)
    {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DateButton=findViewById(R.id.datee)
        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->

            DateButton.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day)
        datePickerDialog.show()
    }
    fun StartTimePicker(view: View)
    {
        val t=findViewById<TextView>(R.id.start)
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val time = String.format("%02d:%02d", selectedHour, selectedMinute)
            t.text = time
        }, hour, minute, true) // true для 24-часового формата
        timePickerDialog.show()
    }
    fun FinishTimePicker(view: View)
    {
        val t=findViewById<TextView>(R.id.finish)
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val time = String.format("%02d:%02d", selectedHour, selectedMinute)
            t.text = time
        }, hour, minute, true) // true для 24-часового формата
        timePickerDialog.show()
    }

    fun redact(view: View)
    {
        val Intent = Intent(this ,ClientEditor::class.java)
        startActivity(Intent)
    }

    fun redact1(view: View)
    {
        val Intent = Intent(this ,EditMovie::class.java)
        startActivity(Intent)
    }

}