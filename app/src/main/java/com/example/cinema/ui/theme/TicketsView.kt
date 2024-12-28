package com.example.cinema.ui.theme

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.SearchClient

class TicketsView(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
    val title: TextView = view.findViewById(R.id.title1)
    val startTime: TextView = view.findViewById(R.id.startTime)
    val finishTime: TextView = view.findViewById(R.id.finishTime)
    val date: TextView = view.findViewById(R.id.date)
    val buyTicket: Button = view.findViewById(R.id.buyTicket)

    fun bind(movie: Movie) {
        title.text = movie.title
        startTime.text = movie.start
        finishTime.text = movie.finish
        date.text = movie.data

        buyTicket.setOnClickListener {
            val intent = Intent(context, SearchClient::class.java)
            context.startActivity(intent)
        }
    }
}