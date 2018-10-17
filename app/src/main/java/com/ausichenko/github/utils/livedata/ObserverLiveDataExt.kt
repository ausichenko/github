package com.ausichenko.github.utils.livedata

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun <Data, Error> ObserverLiveData<Data, Error>.isSuccess(): LiveData<Boolean> {
    return Transformations.map(this, Function {
        return@Function value?.state == ObserverLiveData.DataState.SUCCESS
    })
}

fun <Data, Error> ObserverLiveData<Data, Error>.isLoading(): LiveData<Boolean> {
    return Transformations.map(this, Function {
        return@Function value?.state == ObserverLiveData.DataState.LOADING
    })
}

fun <Data, Error> ObserverLiveData<Data, Error>.isError(): LiveData<Boolean> {
    return Transformations.map(this, Function {
        return@Function value?.state == ObserverLiveData.DataState.ERROR
    })
}

fun <Data, Error> ObserverLiveData<Data, Error>.isEmpty(): LiveData<Boolean> {
    return Transformations.map(this, Function {
        return@Function value?.state == ObserverLiveData.DataState.EMPTY
    })
}