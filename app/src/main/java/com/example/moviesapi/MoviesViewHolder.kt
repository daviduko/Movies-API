package com.example.moviesapi

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapi.databinding.FilmLayoutBinding

class MoviesViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val binding = FilmLayoutBinding.bind(view)

    fun bind(film : Film, onClickListener : (Film) -> Unit){
        binding.FilmName.text = film.title
        binding.FilmYear.text = film.release_date
        Glide.with(binding.poster.context).load(film.poster_path).into(binding.poster)

        itemView.setOnClickListener { onClickListener(film) }
    }
}