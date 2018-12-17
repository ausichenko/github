package com.ausichenko.github.view.search

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.SingleLiveEvent
import com.ausichenko.github.utils.rxconnection.RxNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@Suppress("unused")
class SearchViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private val disposable = CompositeDisposable()

    val isOnline = MutableLiveData<Boolean>()
    var searchQuery = MutableLiveData<String>()
    var searchEvent = SingleLiveEvent<Any>()

    init {
        searchQuery.value = ""
    }

    fun onSearch() {
        searchEvent.call()
    }

    fun initNetworkObserver(context: Context) {
        disposable.add(RxNetwork.stream(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                isOnline.postValue(it)
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}