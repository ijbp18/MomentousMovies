package com.home.momentousmovies.ui.movieList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.home.momentousmovies.R
import com.home.momentousmovies.model.Movie

class MoviesAdapter: RecyclerView.Adapter<MovieViewHolder>() {

    private var movies: List<Movie> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_container_movie,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bind(movies[position])

    fun setData(movieList: List<Movie>){
        movies = movieList
        notifyDataSetChanged()
    }

}