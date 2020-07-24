package com.home.momentousmovies.ui.movieList.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.home.momentousmovies.R
import com.home.momentousmovies.model.Movie
import com.squareup.picasso.Picasso

class MovieViewHolder(
    private val view: View
): RecyclerView.ViewHolder(view) {

    fun bind(item: Movie) {

        val movieImageView = view.findViewById<ImageView>(R.id.image_movie)
        Picasso.get().load(item.image).fit().into(movieImageView)
        val title = view.findViewById<TextView>(R.id.txtMovieTitle)
        title.text = item.title


    }
}