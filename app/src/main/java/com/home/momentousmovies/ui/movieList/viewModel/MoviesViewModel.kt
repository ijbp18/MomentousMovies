package com.home.momentousmovies.ui.movieList.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.data.model.TokenReponse
import com.home.momentousmovies.domain.MovieRepository
import com.home.momentousmovies.domain.TokenRepository
import com.home.momentousmovies.model.Movie
import com.home.momentousmovies.utils.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class MoviesViewModel(private val repository: MovieRepository, private val repositoryToken: TokenRepository, private val context: Context) : ViewModel() {


    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    init {
        println("***ENTRO AL VIEWMODEL")
        retrieveToken()
    }

    private fun retrieveToken() {
        viewModelScope.launch{
            val result: OperationResult<TokenReponse> = withContext(Dispatchers.IO){

                repositoryToken.getToken()
            }

            when (result) {
                is OperationResult.Success -> {
                    _token.value = result.data?.key
                    retrieveMovies()
                }
                is OperationResult.Error -> {
                    _onMessageError.value = result.throwable.message
                }
            }
        }
    }

    private fun retrieveMovies() {
        viewModelScope.launch {
            val result: OperationResult<List<Movie>> = withContext(Dispatchers.IO) {
                val headers: MutableMap<String, String> = HashMap()
                headers["api-key"] = API_KEY
                repository.getMovies(headers)
            }

            when (result) {
                is OperationResult.Success -> {
                    if (result.data.isNullOrEmpty()) {
                        _isEmptyList.value = true
                    } else {
                        _movies.value = result.data
                    }
                }
                is OperationResult.Error -> {
                    _onMessageError.value = result.throwable.message
                }
            }
        }

    }

}

