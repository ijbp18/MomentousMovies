package com.home.momentousmovies.data.datasource.repository

import com.home.momentousmovies.data.datasource.DataSourceImpl
import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.model.Movie
import com.home.momentousmovies.utils.Constants.FAILURE_CONNECTION
import com.home.momentousmovies.utils.Constants.FAILURE_CUSTOM
import com.home.momentousmovies.utils.NetworkHandler
import java.lang.Exception

class MovieRepositoryImpl(private val networkHandler: NetworkHandler,  private val remoteDataSource: DataSourceImpl) :
    MovieRepository {

    override suspend fun getMovies(): OperationResult<List<Movie>> {

        return try {
            when (networkHandler.isConnected) {
                true -> remoteDataSource.getMovies()
                false -> OperationResult.CustomError(FAILURE_CONNECTION)
                else -> OperationResult.CustomError(FAILURE_CUSTOM)
            }
        } catch (ex: Exception) {
            OperationResult.Error(ex)
        }
    }

    override suspend fun getMoviesBySort(typeSort: String): OperationResult<List<Movie>> {
        return remoteDataSource.getMoviesBySort(typeSort)
    }

    override suspend fun getMoviesByPage(page: Int): OperationResult<List<Movie>> {
        return remoteDataSource.getMoviesByPage(page)
    }

    override suspend fun getSelectedMovie(movieId: Int): OperationResult<Movie> {
        return remoteDataSource.getSelectedMovie(movieId)
    }

}