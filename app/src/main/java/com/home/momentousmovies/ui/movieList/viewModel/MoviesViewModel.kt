package com.home.momentousmovies.ui.movieList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.data.network.model.Token
import com.home.momentousmovies.domain.MovieRepository
import com.home.momentousmovies.domain.TokenRepository
import com.home.momentousmovies.model.Movie
import com.home.momentousmovies.model.MovieInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MoviesViewModel(
    private val repository: MovieRepository,
    private val repositoryToken: TokenRepository
) : ViewModel() {

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _movies = MutableLiveData<OperationResult<List<Movie>>>()
    val movies: LiveData<OperationResult<List<Movie>>> = _movies

    private var _selectedMovie = MutableLiveData<OperationResult<MovieInfo>>()
    val selectedMovie: LiveData<OperationResult<MovieInfo>> = _selectedMovie

    init {
        retrieveToken()
    }

    private fun retrieveToken() {

        viewModelScope.launch {
            val result: OperationResult<Token> = withContext(Dispatchers.IO) {
                repositoryToken.getToken()
            }

            when (result) {
                is OperationResult.Success -> {
                    retrieveMovies()
                }
                is OperationResult.Error -> {
                    _token.value = result.message
                }

            }
        }
    }

    private fun retrieveMovies() {

        _movies.value = OperationResult.Loading()
        viewModelScope.launch {
            val result: OperationResult<List<Movie>> = withContext(Dispatchers.IO) {
                repository.getMovies()
            }
            _movies.value = result
        }
    }

    fun retrieveMoviesBySort(sortType: String) {
        _movies.value = OperationResult.Loading()
        viewModelScope.launch {
            val result: OperationResult<List<Movie>> = withContext(Dispatchers.IO) {
                repository.getMoviesBySort(sortType)
            }
            _movies.value = result
        }
    }

    fun retrieveSelectedMovie(idMovie : Int) {

        _selectedMovie.value = OperationResult.Loading()
        viewModelScope.launch {
            val result: OperationResult<MovieInfo> = withContext(Dispatchers.IO) {
                repository.getSelectedMovie(idMovie)
            }
            _selectedMovie.value = result
        }
    }

}

