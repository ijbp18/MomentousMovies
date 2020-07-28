package com.home.momentousmovies.domain

import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.model.Movie
import com.home.momentousmovies.model.MovieInfo

interface MovieRepository {
    suspend fun getMovies(): OperationResult<List<Movie>>
    suspend fun getSelectedMovie(movieId: Int): OperationResult<MovieInfo>
}