package com.home.momentousmovies.ui.movieList.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.home.momentousmovies.domain.MovieRepository

class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesViewModel(repository) as T
    }
}