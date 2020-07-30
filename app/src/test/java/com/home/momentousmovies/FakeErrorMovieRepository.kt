package com.home.momentousmovies

import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.data.datasource.repository.MovieRepository
import com.home.momentousmovies.domain.model.Movie


class FakeErrorMovieRepository: MovieRepository {

    private val mockException = Exception("Ocurrió un error")

    override suspend fun getMovies(): OperationResult<List<Movie>> {
        return OperationResult.Error(mockException)
    }

    override suspend fun getMoviesBySort(typeSort: String): OperationResult<List<Movie>> {
        return OperationResult.Error(mockException)
    }

    override suspend fun getSelectedMovie(movieId: Int): OperationResult<Movie> {
        return OperationResult.Error(mockException)
    }
}