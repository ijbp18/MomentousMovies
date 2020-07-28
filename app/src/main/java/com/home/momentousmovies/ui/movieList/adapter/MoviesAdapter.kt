package com.home.momentousmovies.ui.movieList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView

import androidx.recyclerview.widget.RecyclerView
import com.home.momentousmovies.data.network.Endpoints.GET_IMAGE
import com.home.momentousmovies.data.network.Endpoints.URL_BASE
import com.home.momentousmovies.databinding.ItemContainerMovieBinding
import com.home.momentousmovies.model.Movie
import com.home.momentousmovies.utils.buildImageUrl
import com.home.momentousmovies.utils.loadImage

class MoviesAdapter(private val listener: ItemSelectedListener) :
    RecyclerView.Adapter<MoviesAdapter.ShowNewsViewHolder>() {

    private var movies: List<Movie> = arrayListOf()
    private lateinit var binding: ItemContainerMovieBinding

    interface ItemSelectedListener {
        fun onMovieSelected(movie: Movie, sharedImageView: ImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowNewsViewHolder {
        binding = ItemContainerMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowNewsViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ShowNewsViewHolder, position: Int) =
        holder.bind(movies[position])

    fun removeAll() {
        movies = emptyList()
        notifyDataSetChanged()
    }

    fun setData(movieList: List<Movie>) {
        movies = movieList
        notifyDataSetChanged()
    }



    inner class ShowNewsViewHolder(private val binding: ItemContainerMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) = with(binding) {
            imageMovie.loadImage(movie.image.buildImageUrl(URL_BASE, GET_IMAGE))
            txtMovieTitle.text = movie.title

            root.setOnClickListener {
                listener.onMovieSelected(movie, imageMovie)
            }
        }
    }

}