package com.ausichenko.github.utils.ext

import com.ausichenko.github.data.exceptions.EmptySearchException
import retrofit2.Response

inline fun <reified T> Response<T>.checkResult() {
    checkResponseCode()
}

fun <T> Response<T>.checkResponseCode() {
    when (code()) {
        422 -> throw EmptySearchException()
    }
}