package com.home.momentousmovies.domain

import com.home.momentousmovies.data.network.ApiService
import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.model.Movie
import java.lang.Exception

class MovieRepositoryImpl(private val apiService: ApiService) : MovieRepository {

    override suspend fun getMovies(): OperationResult<List<Movie>> {//header: MutableMap<String, String>

        try {
            val response = apiService.getMovies()//header
            response.let {
                if(it.isSuccessful){
                    it.body()?.let {movies ->
                       return OperationResult.Success(movies)
                    }
                }else{
                    val message = it.errorBody().toString()
                   return OperationResult.Error(Exception(message))
                }
            }?:run{
                return OperationResult.Error(Exception("Ocurrió un error"))
            }
        }catch (e:Exception){
            return OperationResult.Error(e)
        }

    }

}