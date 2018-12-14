package com.ausichenko.github.utils.livedata

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun <Data, Error> SingleLiveData<Data, Error>.isSuccess(): LiveData<Boolean> {
    return Transformations.map(this, Function {
        return@Function value?.state == SingleLiveData.DataState.SUCCESS
    })
}

fun <Data, Error> SingleLiveData<Data, Error>.isLoading(): LiveData<Boolean> {
    return Transformations.map(this, Function {
        return@Function value?.state == SingleLiveData.DataState.LOADING
    })
}

fun <Data, Error> SingleLiveData<Data, Error>.isError(): LiveData<Boolean> {
    return Transformations.map(this, Function {
        return@Function value?.state == SingleLiveData.DataState.ERROR
    })
}

fun <Data, Error> SingleLiveData<Data, Error>.isEmpty(): LiveData<Boolean> {
    return Transformations.map(this, Function {
        return@Function value?.state == SingleLiveData.DataState.EMPTY
    })
}