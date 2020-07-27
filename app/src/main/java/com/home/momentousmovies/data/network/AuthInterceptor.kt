package com.home.momentousmovies.data.network

import com.home.momentousmovies.utils.Constants.API_KEY
import com.home.momentousmovies.utils.Constants.API_KEY_NAME
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        // Request customization: add request headers
        val requestBuilder: Request.Builder = originalRequest.newBuilder()
            .addHeader(API_KEY_NAME, API_KEY)

        return chain.proceed(requestBuilder.build())
    }
}