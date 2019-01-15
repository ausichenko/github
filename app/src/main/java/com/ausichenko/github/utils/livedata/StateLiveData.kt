package com.ausichenko.github.utils.livedata

import androidx.lifecycle.MutableLiveData

class StateLiveData<Data>: MutableLiveData<State<Data>>() {

    init {
        value = State()
    }

    fun isLoading(): Boolean {
        return value?.state == DataState.LOADING
    }

    fun prepareLoading() {
        value?.state = DataState.LOADING
        postValue(value)
    }

    fun prepareSuccess(data: Data) {
        value?.state = DataState.SUCCESS
        value?.data = data
        postValue(value)
    }

    fun prepareError(error: Throwable) {
        value?.state = DataState.ERROR
        value?.error = error
        postValue(value)
    }
}