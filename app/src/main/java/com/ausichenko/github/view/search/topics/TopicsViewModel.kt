package com.ausichenko.github.view.search.topics

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.models.Topic
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.ObserverLiveData
import io.reactivex.android.schedulers.AndroidSchedulers

class TopicsViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private lateinit var topics: ObserverLiveData<List<Topic>>

    fun getTopics(searchQueryLiveData: LiveData<String>): ObserverLiveData<List<Topic>> {
        if (!::topics.isInitialized) {
            topics = ObserverLiveData()
            loadTopics(searchQueryLiveData)
        }
        return topics
    }

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