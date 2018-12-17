package com.ausichenko.github.data.network.interceptors

import android.content.Context
import com.ausichenko.github.data.exceptions.NoNetworkException
import com.ausichenko.github.utils.rxconnection.RxNetwork
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!RxNetwork.getConnectivityStatus(context)) {
            throw NoNetworkException()
        }
        return chain.proceed(request)
    }

}