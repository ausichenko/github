package com.ausichenko.github.view.search.topics

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.network.models.GitResponse
import com.ausichenko.github.data.network.models.Topic
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class TopicsViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private val disposable = CompositeDisposable()

    var topics: ObserverLiveData<GitResponse<Topic>, Throwable> = ObserverLiveData()
    val isSuccess: LiveData<Boolean> = topics.isSuccess()
    val isLoading: LiveData<Boolean> = topics.isLoading()
    val isError: LiveData<Boolean> = topics.isError()
    val isEmpty: LiveData<Boolean> = topics.isEmpty()

    fun loadTopics(searchQueryLiveData: LiveData<String>) {
        val query = searchQueryLiveData.value.toString()
        loadTopics(query)
    }

    private fun loadTopics(searchQuery: String) {
        disposable.add(interactor.getTopics(searchQuery)
            .doOnSubscribe {
                topics.load()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(topics)
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}