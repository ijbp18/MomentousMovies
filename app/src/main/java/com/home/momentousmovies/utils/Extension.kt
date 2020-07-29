package com.home.momentousmovies.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.google.android.material.snackbar.Snackbar
import com.home.momentousmovies.R
import com.home.momentousmovies.utils.Constants.API_KEY
import com.home.momentousmovies.utils.Constants.API_KEY_NAME

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

//Ui snackbar extension
fun Context.snackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) =
    Snackbar.make(view, message, duration).apply { show() }

//Validate Internet Connection
val Context.networkInfo: NetworkInfo? get() = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo


