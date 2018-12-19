package com.ausichenko.github.utils.rxconnection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

internal class OnSubscribeBroadcastRegister(
    private val context: Context,
    private val intentFilter: IntentFilter,
    private val broadcastPermission: String?,
    private val schedulerHandler: Handler?
) : ObservableOnSubscribe<Intent> {

    @Throws(Exception::class)
    override fun subscribe(e: ObservableEmitter<Intent>) {
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                e.onNext(intent)
            }
        }
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(object : Disposable {
            override fun dispose() {
                context.unregisterReceiver(broadcastReceiver)
            }

            override fun isDisposed(): Boolean {
                return false
            }
        })
        context.registerReceiver(
            broadcastReceiver,
            intentFilter,
            broadcastPermission,
            schedulerHandler
        )
    }
}