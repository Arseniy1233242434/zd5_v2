package com.example.cinema.ui.theme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R

class GenreAdapter(private val groupedMovies: Map<String, List<Movie>>) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genreTitle: TextView = itemView.findViewById(R.id.genreTitle)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.moviesRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = groupedMovies.keys.elementAt(position)
        holder.genreTitle.text = genre

        val movies = groupedMovies[genre] ?: emptyList()
        holder.recyclerView.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        holder.recyclerView.adapter = MovieAdapterClient(movies)
    }

    override fun getItemCount(): Int = groupedMovies.size
}
