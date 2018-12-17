package com.ausichenko.github.view.search.topics

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.models.Topic
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.ObserverLiveData
import com.ausichenko.github.utils.livedata.isError
import com.ausichenko.github.utils.livedata.isLoading
import io.reactivex.android.schedulers.AndroidSchedulers

class TopicsViewModel(private val interactor: SearchInteractor) : ViewModel() {

    var topics: ObserverLiveData<List<Topic>> = ObserverLiveData()
    val isLoading: LiveData<Boolean> = topics.isLoading()
    val isError: LiveData<Boolean> = topics.isError()

    fun loadTopics(searchQueryLiveData: LiveData<String>) {
        val query = searchQueryLiveData.value.toString()
        loadTopics(query)
    }

    private fun loadTopics(searchQuery: String) {
        interactor.getTopics(searchQuery)
            .doOnSubscribe {
                topics.load()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(topics)
    }
}