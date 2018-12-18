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

class SearchViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private val disposable = CompositeDisposable()

    var searchQuery = MutableLiveData<String>()
    var searchEvent = SingleLiveEvent<Any>()
    val searchHistory = MutableLiveData<List<String>>()
    val isOnline = MutableLiveData<Boolean>()

    init {
        searchQuery.value = ""
        searchHistory.value = ArrayList()
    }

    fun onSearch() {
        searchEvent.call()
        saveSearchQuery()
        loadSearchHistory()
    }

    private fun saveSearchQuery() {
        disposable.add(
            interactor.saveSearchHistory(searchQuery.value.toString())
                .subscribe()
        )
    }

    fun loadSearchHistory() {
        disposable.add(
            interactor.getSearchHistory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { list -> searchHistory.postValue(list) },
                    { searchHistory.postValue(ArrayList()) }
                )
        )
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