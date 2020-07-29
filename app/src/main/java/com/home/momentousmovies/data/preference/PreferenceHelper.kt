package com.home.momentousmovies.data.preference


interface PreferenceHelper {

    fun getAccessToken(): String?
    fun setAccessToken(accessToken: String?)

}