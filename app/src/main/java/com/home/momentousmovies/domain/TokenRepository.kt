package com.home.momentousmovies.domain

import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.data.network.model.Token

interface TokenRepository {
    suspend fun getToken(): OperationResult<Token>
}