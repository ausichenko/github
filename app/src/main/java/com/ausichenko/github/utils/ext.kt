package com.ausichenko.github.utils

import android.util.Log

fun Any?.logd(message: String) {
    if (this != null) {
        Log.d(this::class.java.name.toString(), message)
    }
}