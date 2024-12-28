package com.example.cinema.ui.theme

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.Buying
import com.example.cinema.R
import com.example.cinema.SearchClient
import com.squareup.picasso.Picasso

class MovieAdapterClient (private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapterClient.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title1)
        val imageView: ImageView = itemView.findViewById(R.id.imageView1)
        val button: Button = itemView.findViewById(R.id.buyticket)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movieclient, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.title
        holder.button.setOnClickListener {
            val intent = Intent(holder.itemView.context, Buying::class.java)
            holder.itemView.context.startActivity(intent)
        }
        Picasso.get()
            .load(movie.photoUrl)
            .into(holder.imageView)
        // Загрузите изображение с помощью библиотеки, например, Glide или Picasso
            // Glide.with(holder.itemView.context).load(movie.photoUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int = movies.size
}
