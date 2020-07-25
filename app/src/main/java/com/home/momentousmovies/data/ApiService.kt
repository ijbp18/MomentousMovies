package com.home.momentousmovies.data

import com.home.momentousmovies.data.Endpoints.GET_KEY
import com.home.momentousmovies.data.Endpoints.GET_MOVIES
import com.home.momentousmovies.data.model.TokenReponse
import com.home.momentousmovies.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query

interface ApiService {

    @GET(GET_KEY)
    suspend fun getToken(@Query("email") key: String): Response<TokenReponse>

    @GET(GET_MOVIES)
    suspend fun getMovies(@HeaderMap headers : Map<String, String>): Response<List<Movie>>

}