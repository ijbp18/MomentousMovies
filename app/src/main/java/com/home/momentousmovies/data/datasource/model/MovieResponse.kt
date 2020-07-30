package com.home.momentousmovies.data.datasource.model

import com.home.momentousmovies.domain.model.Cast
import com.home.momentousmovies.domain.model.Movie
import com.home.momentousmovies.domain.model.Review


class MovieResponse(
    val id: Int,
    val title: String,
    val adult: Boolean,
    val image: String?,
    val description: String,
    val tag: String?,
    val release_date: String?,
    val status: String?,
    val reviews: List<ReviewResponse>?,
    val cast: List<CastResponse>?
)

data class CastResponse(
    val actor: String,
    val character: String,
    val image: String
)

data class ReviewResponse(
    val author: String,
    val value: String
)

fun ReviewResponse.toDomain() = Review(author = author, value = value)

fun CastResponse.toDomain() = Cast(actor = actor, character = character, image = image)

fun MovieResponse.toDomain() = Movie(
    id = id,
    title = title,
    adult = adult,
    image = image,
    description = description,
    tag = tag,
    release_date = release_date,
    status = status,
    reviews = reviews?.map { it.toDomain() },
    cast = cast?.map { it.toDomain() }

)