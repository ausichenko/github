package com.ausichenko.github.data.network.interceptors

import android.content.Context
import com.ausichenko.github.data.exceptions.NoNetworkException
import com.ausichenko.github.utils.NetworkUtil
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        if (!NetworkUtil.isOnline(context)) {
            throw NoNetworkException()
        }

        return chain!!.proceed(chain.request())
    }
}