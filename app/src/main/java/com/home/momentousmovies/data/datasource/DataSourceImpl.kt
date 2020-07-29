package com.home.momentousmovies.data.datasource

import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.data.datasource.model.toDomain
import com.home.momentousmovies.domain.model.Movie
import com.home.momentousmovies.utils.Constants.FAILURE_CUSTOM
import java.lang.Exception

class DataSourceImpl(private val apiService: ApiService) : DataSource{

    override suspend fun getMovies(): OperationResult<List<Movie>> {
        try {
            val response = apiService.getMovies()
            response.let { movieResponse ->
                if (movieResponse.isSuccessful) {
                    movieResponse.body()?.let { movies ->
                        return OperationResult.Success(movies.map { it.toDomain() })
                    }
                } else {
                    val message = movieResponse.errorBody().toString()
                    return OperationResult.Error(Exception(message))
                }
            } ?: run {
                return OperationResult.CustomError(FAILURE_CUSTOM)
            }
        } catch (e: Exception) {
            return OperationResult.Error(e)
        }

    }

    override suspend fun getMoviesBySort(typeSort: String): OperationResult<List<Movie>> {

        try {
            val response = apiService.getMoviesBySort(typeSort)
            response.let {
                if (it.isSuccessful) {
                    it.body()?.let { movies ->
                        return OperationResult.Success(
                            movies.map {movieDTO ->  movieDTO.toDomain() }
                        )
                    }
                } else {
                    val message = it.errorBody().toString()
                    return OperationResult.Error(
                        Exception(message)
                    )
                }
            } ?: run {
                return OperationResult.Error(
                    Exception(FAILURE_CUSTOM)
                )
            }
        } catch (e: Exception) {
            return OperationResult.Error(e)
        }

    }

    override suspend fun getSelectedMovie(movieId: Int): OperationResult<Movie> {
        try {
            val response = apiService.getMovie(movieId)//header
            response.let {
                if (it.isSuccessful) {
                    it.body()?.let { movie ->
                        return OperationResult.Success(movie.toDomain())
                    }
                } else {
                    val message = it.errorBody().toString()
                    return OperationResult.Error(
                        Exception(message)
                    )
                }
            } ?: run {
                return OperationResult.Error(
                    Exception(FAILURE_CUSTOM)
                )
            }
        } catch (e: Exception) {
            return OperationResult.Error(e)
        }
    }

}