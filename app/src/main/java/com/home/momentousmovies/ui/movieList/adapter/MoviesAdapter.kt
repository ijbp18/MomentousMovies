package com.home.momentousmovies.ui.movieList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.home.momentousmovies.R
import com.home.momentousmovies.data.Endpoints.GET_IMAGE
import com.home.momentousmovies.data.Endpoints.URL_BASE
import com.home.momentousmovies.databinding.ItemContainerMovieBinding
import com.home.momentousmovies.model.Movie
import com.squareup.picasso.Picasso

class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.ShowNewsViewHolder>() {

    private var movies: List<Movie> = arrayListOf()
    private lateinit var binding: ItemContainerMovieBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowNewsViewHolder {
        binding = ItemContainerMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowNewsViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ShowNewsViewHolder, position: Int) = holder.bind(movies[position])

    fun setData(movieList: List<Movie>){
        movies = movieList
        notifyDataSetChanged()
    }

    inner class ShowNewsViewHolder(private val binding: ItemContainerMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) = with(binding) {

            val imageUrl = StringBuilder(URL_BASE).append(GET_IMAGE).append(movie.image).toString()
            Picasso.get().load(imageUrl).fit().into(imageMovie)
            txtMovieTitle.text = movie.title

        }
    }

}