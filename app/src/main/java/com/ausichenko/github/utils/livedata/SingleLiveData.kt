package com.ausichenko.github.utils.livedata

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiConsumer
import io.reactivex.functions.Consumer

class SingleLiveData<Data, Error> : MutableLiveData<SingleLiveData.State<Data, Error>>(),
    BiConsumer<Data, Error> {

    init {
        value = SingleLiveData.State()
    }

    override fun accept(data: Data, error: Error) {
        when {
            data != null -> {
                value?.state = SingleLiveData.DataState.SUCCESS
                value?.data = data
            }
            error != null -> {
                value?.state = SingleLiveData.DataState.ERROR
                value?.error = error
            }
            else -> {
                value?.state = SingleLiveData.DataState.EMPTY
            }
        }
        notifyObserver()
    }

    fun load() {
        value?.state = SingleLiveData.DataState.LOADING
        notifyObserver()
    }

    private fun notifyObserver() {
        postValue(value)
    }

    class State<Data, Error> {
        var state = DataState.LOADING
        var data: Data? = null
        var error: Error? = null
    }

    enum class DataState {
        SUCCESS,
        LOADING,
        ERROR,
        EMPTY
    }
}