package com.home.momentousmovies.data.datasource

import com.home.momentousmovies.data.datasource.Endpoints.GET_KEY
import com.home.momentousmovies.data.datasource.Endpoints.GET_MOVIE
import com.home.momentousmovies.data.datasource.Endpoints.GET_MOVIES
import com.home.momentousmovies.data.datasource.model.MovieResponse
import com.home.momentousmovies.data.datasource.model.TokenResponse
import com.home.momentousmovies.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(GET_KEY)
    suspend fun getToken(@Query("email") key: String): Response<TokenResponse>

    @GET(GET_MOVIES)
    suspend fun getMovies(): Response<List<MovieResponse>>

    @GET(GET_MOVIES)
    suspend fun getMoviesBySort(@Query("sort") typeSort: String): Response<List<MovieResponse>>

    @GET(GET_MOVIES)
    suspend fun getMoviesByPage(@Query("page") typeSort: Int): Response<List<MovieResponse>>

    @GET(GET_MOVIE)
    suspend fun getMovie(@Path("id") key: Int): Response<MovieResponse>

}