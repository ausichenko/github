package com.ausichenko.github.utils.ext

import android.util.Log

fun Any?.logv(message: String) {
    if (this != null) {
        Log.v(this::class.java.name.toString(), message)
    }
}

fun Any?.logd(message: String) {
    if (this != null) {
        Log.d(this::class.java.name.toString(), message)
    }
}

fun Any?.logi(message: String) {
    if (this != null) {
        Log.i(this::class.java.name.toString(), message)
    }
}

fun Any?.logw(message: String) {
    if (this != null) {
        Log.w(this::class.java.name.toString(), message)
    }
}

fun Any?.loge(message: String) {
    if (this != null) {
        Log.e(this::class.java.name.toString(), message)
    }
}

fun Any?.logwtf(message: String) {
    if (this != null) {
        Log.wtf(this::class.java.name.toString(), message)
    }
}