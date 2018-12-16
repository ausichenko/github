package com.ausichenko.github.utils.rxconnection

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import io.reactivex.Observable

object RxNetwork {

    fun getConnectivityStatus(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return null != activeNetwork && activeNetwork.isConnected
    }

    fun stream(context: Context): Observable<Boolean> {
        val applicationContext = context.applicationContext
        val action = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        return ContentObservable.fromBroadcast(context, action)
            .map(object : Function1<Intent, Boolean> {
                override fun invoke(p1: Intent): Boolean {
                    return getConnectivityStatus(applicationContext)
                }
            }).distinctUntilChanged()
    }
}