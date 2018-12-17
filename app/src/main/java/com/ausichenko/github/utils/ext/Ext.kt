package com.ausichenko.github.utils.ext

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

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

fun TextView.dismissKeyboard() {
    clearFocus()
    val imm = (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}