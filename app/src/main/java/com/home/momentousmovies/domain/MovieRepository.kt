package com.home.momentousmovies.domain

import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.model.Movie

interface MovieRepository {
    fun getMovies() : OperationResult<List<Movie>>
}