package com.example.moviesapi

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewAdapter(var films : List<Film>, private val onClickListener : (Film) -> Unit) : RecyclerView.Adapter<MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MoviesViewHolder(layoutInflater.inflate(R.layout.film_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return films.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val item = films[position]
        holder.bind(item, onClickListener)

        //Lo siguiente es la forma facil de hacerlo sin tener que: pasar una funcion lambda al adapter y este al holder para que se llame al clickar al itemView

        /*holder.itemView.setOnClickListener{
            val context = holder.itemView.context
            val intent = Intent(context, FilmData::class.java)
            intent.putExtra("synopsis", item.overview)
            context.startActivity(intent)
        }*/
    }
}