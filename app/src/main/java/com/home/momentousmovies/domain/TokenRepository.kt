package com.home.momentousmovies.domain

import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.data.model.TokenReponse
import com.home.momentousmovies.model.Movie

interface TokenRepository {
    suspend fun getToken(): OperationResult<TokenReponse>
}