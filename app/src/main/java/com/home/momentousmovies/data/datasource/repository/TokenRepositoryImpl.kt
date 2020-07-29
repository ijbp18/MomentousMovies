package com.home.momentousmovies.data.datasource.repository

import com.home.momentousmovies.data.datasource.ApiService
import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.data.datasource.model.TokenResponse
import com.home.momentousmovies.utils.Constants.FAILURE_CONNECTION
import com.home.momentousmovies.utils.Constants.FAILURE_CUSTOM
import com.home.momentousmovies.utils.Constants.KEY_VALUE
import com.home.momentousmovies.utils.NetworkHandler
import java.lang.Exception

class TokenRepositoryImpl(private val networkHandler: NetworkHandler, private val apiService: ApiService) :
    TokenRepository {
    override suspend fun getToken(): OperationResult<TokenResponse> {

        try {
           return when(networkHandler.isConnected){
                true -> {
                    val response = apiService.getToken(KEY_VALUE)
                    response.let {
                        if (it.isSuccessful) {
                            it.body()?.let { token ->
                                return OperationResult.Success(token)
                            }

                        } else {
                            val message = it.errorBody().toString()
                            return OperationResult.Error(Exception(message))
                        }
                    } ?: run {
                        return OperationResult.CustomError(FAILURE_CUSTOM)
                    }
                }
                false -> return OperationResult.CustomError(FAILURE_CONNECTION)
               else -> OperationResult.CustomError(FAILURE_CUSTOM)
           }

        } catch (e: Exception) {
            return OperationResult.Error(e)
        }
    }


}