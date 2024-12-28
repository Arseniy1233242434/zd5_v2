package com.example.cinema.ui.theme

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R

class TicketAdapter(private val movies: List<Movie>, private val sharedPreferences: SharedPreferences) :
    RecyclerView.Adapter<TicketAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title1)
        val start: TextView = itemView.findViewById(R.id.startTime)
        val finish: TextView = itemView.findViewById(R.id.finishTime)
        val date: TextView = itemView.findViewById(R.id.date)
        val button: Button = itemView.findViewById(R.id.buyTicket)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ticked, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        // Получаем значение title из SharedPreferences
        val titleFromPrefs = sharedPreferences.getString("titlee", "Default Title") ?: "Default Title"
        holder.title.text = movie.title //
        holder.start.text = movie.start
        holder.finish.text = movie.finish
        holder.date.text = movie.data

        holder.button.setOnClickListener {

            Toast.makeText(holder.itemView.context, "Билет на ${movie.title} куплен", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = movies.size
}