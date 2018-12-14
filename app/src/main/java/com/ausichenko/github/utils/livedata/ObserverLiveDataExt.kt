package com.ausichenko.github.utils.livedata

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun <Data> ObserverLiveData<Data>.isSuccess(): LiveData<Boolean> {
    return Transformations.map(this, Function {
        return@Function value?.state == ObserverLiveData.DataState.SUCCESS
    })
}

fun <Data> ObserverLiveData<Data>.isLoading(): LiveData<Boolean> {
    return Transformations.map(this, Function {
        return@Function value?.state == ObserverLiveData.DataState.LOADING
    })
}

fun <Data> ObserverLiveData<Data>.isError(): LiveData<Boolean> {
    return Transformations.map(this, Function {
        return@Function value?.state == ObserverLiveData.DataState.ERROR
    })
}

fun <Data> ObserverLiveData<Data>.isEmpty(): LiveData<Boolean> {
    return Transformations.map(this, Function {
        return@Function value?.state == ObserverLiveData.DataState.EMPTY
    })
}