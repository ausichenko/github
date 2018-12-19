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

    private lateinit var searchHistory: MutableLiveData<List<String>>
    private lateinit var networkStatus: MutableLiveData<Boolean>
    private var searchQuery = MutableLiveData<String>()
    private var searchEvent = SingleLiveEvent<Any>()

    init {
        searchQuery.value = ""
    }

    fun onSearch() {
        searchEvent.call()
        saveSearchQuery()
        loadSearchHistory()
    }

    fun getSearchHistory(): MutableLiveData<List<String>> {
        if (!::searchHistory.isInitialized) {
            searchHistory = MutableLiveData()
            loadSearchHistory()
        }
        return searchHistory
    }

    private fun loadSearchHistory() {
        disposable.add(
            interactor.getSearchHistory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { list -> searchHistory.postValue(list) },
                    { searchHistory.postValue(ArrayList()) }
                )
        )
    }

    private fun saveSearchQuery() {
        disposable.add(
            interactor.saveSearchHistory(searchQuery.value.toString())
                .subscribe()
        )
    }

    fun getNetworkStatus(context: Context): MutableLiveData<Boolean> {
        if (!::networkStatus.isInitialized) {
            networkStatus = MutableLiveData()
            initNetworkStatus(context)
        }
        return networkStatus
    }

    private fun initNetworkStatus(context: Context) {
        disposable.add(RxNetwork.stream(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                networkStatus.postValue(it)
            })
    }

    fun getSearchQuery(): String {
        return searchQuery.value.toString()
    }

    fun setSearchQuery(query: String) {
        searchQuery.postValue(query)
    }

    fun getSearchEvent(): SingleLiveEvent<Any> {
        return searchEvent
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}