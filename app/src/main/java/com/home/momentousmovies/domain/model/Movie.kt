package com.home.momentousmovies.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val adult: Boolean,
    val image: String?,
    val description: String,
    val tag: String?,
    val release_date: String?,
    val status: String?,
    val reviews: List<Review>?,
    val cast: List<Cast>?
)

data class Cast(
    val actor: String,
    val character: String?,
    val image: String?
)

data class Review(
    val author: String,
    val value: String?
)