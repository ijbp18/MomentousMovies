package com.home.momentousmovies.data.model

import com.google.gson.annotations.SerializedName

data class TokenReponse(
    @SerializedName("email")
    val description: String,
    @SerializedName("key")
    val key: String
)