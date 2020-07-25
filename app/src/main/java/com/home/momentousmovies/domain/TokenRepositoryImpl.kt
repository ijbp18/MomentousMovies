package com.home.momentousmovies.domain

import com.home.momentousmovies.data.ApiService
import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.data.model.TokenReponse
import com.home.momentousmovies.model.Movie
import com.home.momentousmovies.utils.Constants.KEY_VALUE
import java.lang.Exception

class TokenRepositoryImpl(private val apiService: ApiService) : TokenRepository {
    override suspend fun getToken(): OperationResult<TokenReponse> {
        try {
            val response = apiService.getToken(KEY_VALUE)
            response.let {
                if(it.isSuccessful){
                    it.body()?.let {token ->
                        return OperationResult.Success(token)
                    }

                }else{
                    val message = it.errorBody().toString()
                    return OperationResult.Error(Exception(message))
                }
            }?:run{
                return OperationResult.Error(Exception("Ocurrió un error al obtener el Token"))
            }
        }catch (e:Exception){
            return OperationResult.Error(e)
        }
    }


}