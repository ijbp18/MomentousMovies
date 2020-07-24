package com.home.momentousmovies.ui.movieList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.home.momentousmovies.domain.MovieRepository
import com.home.momentousmovies.model.Movie

class MoviesViewModel(private val repository: MovieRepository) : ViewModel(){

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

}

