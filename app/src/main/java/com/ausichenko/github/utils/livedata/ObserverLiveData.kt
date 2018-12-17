package com.ausichenko.github.utils.livedata

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ObserverLiveData<Data> : MutableLiveData<ObserverLiveData.State<Data>>(), Observer<Data> {

    init {
        value = ObserverLiveData.State()
    }

    private lateinit var disposable: Disposable

    override fun onComplete() {
        disposable.dispose()
    }

    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onNext(data: Data) {
        value?.state = ObserverLiveData.DataState.SUCCESS
        value?.data = data
        notifyObserver()
    }

    override fun onError(throwable: Throwable) {
        value?.state = ObserverLiveData.DataState.ERROR
        value?.error = throwable
        notifyObserver()
    }

    fun load() {
        value?.state = ObserverLiveData.DataState.LOADING
        notifyObserver()
    }

    private fun notifyObserver() {
        postValue(value)
    }

    class State<Data> {
        var state = DataState.LOADING
        var data: Data? = null
        var error: Throwable? = null
    }

    enum class DataState {
        SUCCESS,
        LOADING,
        ERROR
    }
}