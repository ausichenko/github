package com.ausichenko.github.utils

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.ausichenko.github.data.exeptions.FieldException
import retrofit2.Response

fun Any?.logd(message: String) {
    if (this != null) {
        Log.d(this::class.java.name.toString(), message)
    }
}

fun TextView.dismissKeyboard() {
    clearFocus()
    val imm = (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

inline fun <reified T> Response<T>.checkResult() {
    checkResponseCode()
}

fun <T> Response<T>.checkResponseCode() {
    when (code()) {
        422 -> throw FieldException()
    }
}