package com.home.momentousmovies.ui.movieList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.data.datasource.model.TokenResponse
import com.home.momentousmovies.data.datasource.repository.MovieRepository
import com.home.momentousmovies.data.datasource.repository.TokenRepository
import com.home.momentousmovies.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MoviesViewModel(
    private val repository: MovieRepository,
    private val repositoryToken: TokenRepository
) : ViewModel() {

    private val _token = MutableLiveData<OperationResult<TokenResponse>>()
    val token: LiveData<OperationResult<TokenResponse>> = _token

    private val _movies = MutableLiveData<OperationResult<List<Movie>>>()
    val movies: LiveData<OperationResult<List<Movie>>> = _movies

    private var _selectedMovie = MutableLiveData<OperationResult<Movie>>()
    val selectedMovie: LiveData<OperationResult<Movie>> = _selectedMovie

    private val _page = MutableLiveData<Int>()

    init {
        retrieveToken()
    }

    private fun retrieveToken() {

        viewModelScope.launch {
            when (val result = repositoryToken.getToken()) {
                is OperationResult.Success -> retrieveMovies()
                else -> _token.value = result
            }

        }
    }

    private fun retrieveMovies() {
        _movies.value = OperationResult.Loading()
        viewModelScope.launch {
            _movies.value = withContext(Dispatchers.IO) { repository.getMovies() }
        }
    }

    fun retrieveMoviesBySort(sortType: String) {
        _movies.value = OperationResult.Loading()
        viewModelScope.launch {
            _movies.value = withContext(Dispatchers.IO) { repository.getMoviesBySort(sortType) }
        }
    }

    fun retrieveMoviesByPage(page: Int) {
        _movies.value = OperationResult.Loading()
        viewModelScope.launch {
            val result = repository.getMoviesByPage(page)
            _movies.value = result
        }
    }

    fun retrieveSelectedMovie(idMovie: Int) {

        _selectedMovie.value = OperationResult.Loading()
        viewModelScope.launch {
            val result: OperationResult<Movie> = withContext(Dispatchers.IO) {
                repository.getSelectedMovie(idMovie)
            }
            _selectedMovie.value = result
        }
    }

}

