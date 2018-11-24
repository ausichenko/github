package com.ausichenko.github.view.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.SingleLiveEvent

@Suppress("unused")
class SearchViewModel(private val interactor: SearchInteractor) : ViewModel() {
    var searchQuery = MutableLiveData<String>()
    var searchEvent = SingleLiveEvent<Any>()

    fun onSearch() {
        searchEvent.call()
    }
}