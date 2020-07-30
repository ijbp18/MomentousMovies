package com.home.momentousmovies

import com.home.momentousmovies.data.OperationResult
import com.home.momentousmovies.data.datasource.model.TokenResponse
import com.home.momentousmovies.data.datasource.repository.MovieRepository
import com.home.momentousmovies.data.datasource.repository.TokenRepository
import com.home.momentousmovies.domain.model.Movie


class FakeTokenRepository : TokenRepository {

    private lateinit var mockItem: TokenResponse

    init {
        mockData()
    }

    private fun mockData() {
        mockItem = TokenResponse("usernamel@email.com", "Movie title 8")
    }

    override suspend fun getToken(): OperationResult<TokenResponse> {
        return OperationResult.Success(mockItem)
    }
}