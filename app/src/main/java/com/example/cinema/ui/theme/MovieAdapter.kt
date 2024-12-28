package com.example.cinema.ui.theme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val MovieViewModel: MovieViewModel,
    private var movies: MutableList<Movie>,
    private val onEditClick: (Movie) -> Unit,
    private val onDeleteClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image:ImageView=itemView.findViewById(R.id.imm)
        val title: TextView = itemView.findViewById(R.id.title)
        val genre: TextView = itemView.findViewById(R.id.genre)
        val releaseDate: TextView = itemView.findViewById(R.id.releaseDate)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val date:TextView=itemView.findViewById(R.id.datee)
        val start:TextView=itemView.findViewById(R.id.startdate)
        val finish:TextView=itemView.findViewById(R.id.finishdate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        Picasso.get()
            .load(movie.photoUrl)
            .into(holder.image)
        holder.title.text = movie.title
        if(movie.data!=null)
        holder.date.text=movie.data

        holder.start.text=movie.start
        holder.finish.text=movie.finish
        holder.genre.text = movie.genre
        holder.releaseDate.text = movie.releaseDate
        holder.editButton.setOnClickListener {
            val updatedUser = movie.copy(start = holder.start.text.toString(), finish = holder.finish.text.toString(), data = holder.date.text.toString())
            updateUserAtPosition(position, updatedUser)
            MovieViewModel.update(updatedUser)
        }
        holder.deleteButton.setOnClickListener { onDeleteClick(movie) }
    }

    override fun getItemCount(): Int = movies.size
    fun updateUserAtPosition(position: Int, updatedUser: Movie) {
        movies[position] = updatedUser
        notifyItemChanged(position)
    }
    fun updateMovies(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }
}