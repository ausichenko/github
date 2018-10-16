package com.ausichenko.github.utils.livedata

import androidx.lifecycle.MutableLiveData
import io.reactivex.functions.BiConsumer

class ObserverLiveData<Data, Error>: MutableLiveData<ObserverLiveData.State<Data, Error>>(), BiConsumer<Data, Error> {
    init {
        value = ObserverLiveData.State()
    }

    override fun accept(data: Data, error: Error) {
        when {
            data != null -> {
                value?.state = ObserverLiveData.DataState.SUCCESS
                value?.stateLD?.postValue(ObserverLiveData.DataState.SUCCESS)
                value?.data = data
            }
            error != null -> {
                value?.state = ObserverLiveData.DataState.ERROR
                value?.stateLD?.postValue(ObserverLiveData.DataState.ERROR)
                value?.error = error
            }
            else -> {
                value?.state = ObserverLiveData.DataState.EMPTY
                value?.stateLD?.postValue(ObserverLiveData.DataState.EMPTY)
            }
        }
        notifyObserver()
    }

    fun load() {
        value?.state = ObserverLiveData.DataState.LOADING
        value?.stateLD?.postValue(ObserverLiveData.DataState.LOADING)
        notifyObserver()
    }

    private fun notifyObserver() {
        postValue(value)
    }

    class State<Data, Error> {
        var state = DataState.LOADING
        var stateLD: MutableLiveData<DataState>? = MutableLiveData()
        var data: Data? = null
        var dataLD: MutableLiveData<Data>? = MutableLiveData()
        var error: Error? = null
        var errorLD: MutableLiveData<Error>? = MutableLiveData()
    }

    enum class DataState {
        SUCCESS,
        LOADING,
        ERROR,
        EMPTY
    }
}