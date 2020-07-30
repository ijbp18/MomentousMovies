package com.home.momentousmovies.ui.movieList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.data.datasource.model.TokenResponse
import com.home.momentousmovies.data.datasource.repository.MovieRepository
import com.home.momentousmovies.data.datasource.repository.TokenRepository
import com.home.momentousmovies.domain.model.Movie
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


    init {
        retrieveToken()
    }

    private fun retrieveToken() {
        _token.value = OperationResult.Loading()
        viewModelScope.launch {
            when (val result =  with(Dispatchers.IO) {repositoryToken.getToken() }) {
                is OperationResult.Success -> {
                    _token.value = result
                    retrieveMovies()
                }
                else -> _token.value = result
            }
        }
    }

    fun retrieveMovies() {
        _movies.value = OperationResult.Loading()
        viewModelScope.launch {
            _movies.value = with(Dispatchers.IO) { repository.getMovies() }
        }
    }

    fun retrieveMoviesBySort(sortType: String) {
        _movies.value = OperationResult.Loading()
        viewModelScope.launch {
            _movies.value = withContext(Dispatchers.IO) { repository.getMoviesBySort(sortType) }
        }
    }

    fun retrieveSelectedMovie(idMovie: Int) {
        _selectedMovie.value = OperationResult.Loading()
        viewModelScope.launch {
            _selectedMovie.value = withContext(Dispatchers.IO) { repository.getSelectedMovie(idMovie) }
        }
    }

}

