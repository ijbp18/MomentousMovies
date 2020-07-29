package com.home.momentousmovies.data.datasource

import com.home.momentousmovies.data.preference.PreferenceHelper
import com.home.momentousmovies.utils.Constants.API_KEY_NAME
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private val preferenceHelper: PreferenceHelper) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder: Request.Builder = originalRequest.newBuilder()
            .addHeader(API_KEY_NAME, preferenceHelper.getAccessToken())
        return chain.proceed(requestBuilder.build())
    }
}