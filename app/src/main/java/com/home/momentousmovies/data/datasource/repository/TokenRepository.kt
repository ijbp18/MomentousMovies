package com.home.momentousmovies.data.datasource.repository

import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.data.datasource.model.TokenResponse

interface TokenRepository {
    suspend fun getToken(): OperationResult<TokenResponse>
}