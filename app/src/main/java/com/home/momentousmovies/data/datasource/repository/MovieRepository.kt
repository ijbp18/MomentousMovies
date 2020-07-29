package com.home.momentousmovies.data.datasource.repository

import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies(): OperationResult<List<Movie>>
    suspend fun getMoviesBySort(typeSort: String): OperationResult<List<Movie>>
    suspend fun getSelectedMovie(movieId: Int): OperationResult<Movie>
}