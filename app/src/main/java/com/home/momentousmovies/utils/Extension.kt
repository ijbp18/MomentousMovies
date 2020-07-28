package com.home.momentousmovies.utils

import android.widget.ImageView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.home.momentousmovies.R
import com.home.momentousmovies.utils.Constants.API_KEY
import com.home.momentousmovies.utils.Constants.API_KEY_NAME
import kotlin.math.abs

fun ImageView.loadImage(urlToImage: String?) {

    val glideUrl = GlideUrl(urlToImage) { mapOf(Pair(API_KEY_NAME, API_KEY)) }
    Glide.with(context)
        .load(glideUrl)
        .centerCrop()
        .placeholder(R.drawable.ic_image_placeholder)
        .into(this)
}

fun String.buildImageUrl(urlBase: String, urlImage: String): String {
    return StringBuilder(urlBase).append(urlImage).append(this).toString()
}


