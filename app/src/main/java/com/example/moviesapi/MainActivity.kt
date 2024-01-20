package com.example.moviesapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapi.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Arrays

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val movieApiService = retrofit.create(MovieAPIService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showRecyclerView()
    }

    private fun showRecyclerView(){
        val recyclerView = binding.recyclerViewFilms
        recyclerView.layoutManager =  GridLayoutManager(this, 1)
        CoroutineScope(Dispatchers.IO).launch {
            val filmsList = showMovies()
            runOnUiThread {
                recyclerView.adapter = RecyclerViewAdapter(filmsList){
                    film -> onFilmSelected(film)
                }
            }
        }
    }

    fun onFilmSelected(film : Film){
        val intent : Intent = Intent(this, FilmData::class.java)
        intent.putExtra("synopsis", film.overview)
        startActivity(intent)
    }

    private suspend fun showMovies() : List<Film> {
        val filmsList  = mutableListOf<Film>()

        for (page in 1..50) {
            try {
                val response = movieApiService.getTopRatedMovies(
                    apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxNzc0ZjIwNjk1OWFmZGM0N2YzYTE0ZGY0N2ZkN2FkNiIsInN1YiI6IjY0N2NiYWM1Y2FlZjJkMDBkZjg5MjFmNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.MzbWdv9BlyGc3b9Mk7qK3qjhsqcYBFBjJkW9Lqn4qBc",
                    language = "es-ES",
                    page = page
                )

                val simplifiedList = response.results.map { movie ->
                    Film(
                        title = movie.title,
                        release_date = movie.release_date.substring(0, 4),
                        overview = movie.overview,
                        poster_path = "https://image.tmdb.org/t/p/w300_and_h450_bestv2/${movie.poster_path}"
                    )
                }

                filmsList.addAll(simplifiedList)

            } catch (e: Exception) {
                //e.printStackTrace()
                Log.d("Fallo", e.message.toString())
            }
        }

        return filmsList
    }
}