package com.ausichenko.github.utils.rxconnection

import android.content.Context
import android.content.IntentFilter
import android.content.Intent
import io.reactivex.Observable


internal class ContentObservable {
    companion object {
        fun fromBroadcast(context: Context, filter: IntentFilter): Observable<Intent> {
            return Observable.create(OnSubscribeBroadcastRegister(context, filter, null, null))
        }
    }
}