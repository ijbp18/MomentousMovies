package com.home.momentousmovies

import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.data.datasource.repository.MovieRepository
import com.home.momentousmovies.domain.model.Movie


class FakeMovieRepository : MovieRepository {

    private val mockList: MutableList<Movie> = mutableListOf()
   private lateinit var mockItem: Movie

    init {
        mockData()
    }

    private fun mockData() {
        mockList.add(Movie(0, "Movie title 1", false, "", "Movie Description", "", "", "", emptyList(), emptyList()))
        mockList.add(Movie(1, "Movie title 2", false, "", "Movie Description", "", "", "", emptyList(), emptyList()))
        mockList.add(Movie(2, "Movie title 3", false, "", "Movie Description", "", "", "", emptyList(), emptyList()))
        mockItem = Movie(5, "Movie title 8", false, "", "Movie Description", "", "", "", emptyList(), emptyList())
    }


    override suspend fun getMovies(): OperationResult<List<Movie>> {
        return OperationResult.Success(mockList)
    }

    override suspend fun getMoviesBySort(typeSort: String): OperationResult<List<Movie>> {
        return OperationResult.Success(mockList)
    }

    override suspend fun getSelectedMovie(movieId: Int): OperationResult<Movie> {
        return OperationResult.Success(mockItem)
    }
}