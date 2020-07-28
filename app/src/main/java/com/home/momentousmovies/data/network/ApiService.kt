package com.home.momentousmovies.data.network

import com.home.momentousmovies.data.network.Endpoints.GET_KEY
import com.home.momentousmovies.data.network.Endpoints.GET_MOVIE
import com.home.momentousmovies.data.network.Endpoints.GET_MOVIES
import com.home.momentousmovies.data.network.model.Token
import com.home.momentousmovies.model.Movie
import com.home.momentousmovies.model.MovieInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(GET_KEY)
    suspend fun getToken(@Query("email") key: String): Response<Token>

    @GET(GET_MOVIES)
    suspend fun getMovies(): Response<List<Movie>>

    @GET(GET_MOVIES)
    suspend fun getMoviesBySort(@Query("sort") type: String): Response<List<Movie>>

    @GET(GET_MOVIE)
    suspend fun getMovie(@Path("id") key: Int): Response<MovieInfo>

}