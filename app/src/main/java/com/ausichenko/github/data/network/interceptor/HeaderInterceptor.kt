package com.ausichenko.github.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("Accept", "application/vnd.github.cloak-preview")
            .build()
        return chain.proceed(request)
    }
}