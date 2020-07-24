package com.home.momentousmovies.model

data class Movie(
    val id: Int,
    val title: String,
    val adult: Boolean,
    val image: String,
    val description: String
)