package com.ausichenko.github.utils.ext

import com.ausichenko.github.data.exceptions.EmptyException
import com.ausichenko.github.data.network.models.GitResponse
import io.reactivex.Single
import retrofit2.Response

inline fun <reified T> Single<Response<GitResponse<T>>>.checkResult(): Single<GitResponse<T>> {
    return this.map {
        it.checkResponseCode()
        it.checkIsEmpty()
        it.body()
    }
}

inline fun <reified T> Response<GitResponse<T>>.checkIsEmpty() {
    if (body() != null && body()!!.totalCount == 0) {
        throw EmptyException()
    }
}

inline fun <reified T> Response<T>.checkResult(): T? {
    checkResponseCode()
    return body()
}

fun <T> Response<T>.checkResponseCode() {
    when (code()) {
        200 -> return
        400 -> badRequest()
        401 -> unauthorized()
        422 -> unprocessableEntity()
        else -> throw RuntimeException()
    }
}

fun <T> Response<T>.badRequest() {
    //parseMessage
}

fun <T> Response<T>.unauthorized() {
    //parseMessage
}

fun <T> Response<T>.unprocessableEntity() {
    //parseMessage
}