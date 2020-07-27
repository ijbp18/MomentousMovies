package com.home.momentousmovies.data.network.model

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("email")
    val description: String,
    @SerializedName("key")
    val key: String
)