package com.home.momentousmovies.model

data class MovieInfo(
    val id: Int,
    val title: String,
    val adult: Boolean,
    val image: String,
    val description: String,
    val tag: String,
    val release_date: String,
    val status: String,
    val reviews: List<Review>,
    val cast: List<Cast>
)