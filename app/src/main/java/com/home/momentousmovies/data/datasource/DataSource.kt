package com.home.momentousmovies.data.datasource

import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.model.Movie

interface DataSource {
    suspend fun getMovies(): OperationResult<List<Movie>>
    suspend fun getMoviesBySort(typeSort: String): OperationResult<List<Movie>>
    suspend fun getSelectedMovie(movieId: Int): OperationResult<Movie>
}