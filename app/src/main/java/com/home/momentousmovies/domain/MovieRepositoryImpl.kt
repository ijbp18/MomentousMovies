package com.home.momentousmovies.domain

import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.model.Movie

class MovieRepositoryImpl: MovieRepository {

    override fun getMovies(): OperationResult<List<Movie>> {
        return OperationResult.Success(emptyList())
    }
}