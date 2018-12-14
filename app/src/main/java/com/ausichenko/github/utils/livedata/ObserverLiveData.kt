package com.ausichenko.github.utils.livedata

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiConsumer
import io.reactivex.functions.Consumer

class ObserverLiveData<Data, Error> : MutableLiveData<ObserverLiveData.State<Data, Error>>(),
    BiConsumer<Data, Error> {

    init {
        value = ObserverLiveData.State()
    }

    override fun accept(data: Data, error: Error) {
        when {
            data != null -> {
                value?.state = ObserverLiveData.DataState.SUCCESS
                value?.data = data
            }
            error != null -> {
                value?.state = ObserverLiveData.DataState.ERROR
                value?.error = error
            }
            else -> {
                value?.state = ObserverLiveData.DataState.EMPTY
            }
        }
        notifyObserver()
    }

    fun load() {
        value?.state = ObserverLiveData.DataState.LOADING
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