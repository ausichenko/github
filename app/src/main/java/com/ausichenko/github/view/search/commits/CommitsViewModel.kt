package com.ausichenko.github.view.search.commits

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.network.models.Commit
import com.ausichenko.github.data.network.models.GitResponse
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class CommitsViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private val disposable = CompositeDisposable()

    var commits: ObserverLiveData<GitResponse<Commit>, Throwable> = ObserverLiveData()
    val isSuccess: LiveData<Boolean> = commits.isSuccess()
    val isLoading: LiveData<Boolean> = commits.isLoading()
    val isError: LiveData<Boolean> = commits.isError()
    val isEmpty: LiveData<Boolean> = commits.isEmpty()

    fun loadCommits(searchQueryLiveData: LiveData<String>) {
        val query = searchQueryLiveData.value.toString()
        loadCommits(query)
    }

    private fun loadCommits(searchQuery: String) {
        disposable.add(interactor.getCommits(searchQuery)
            .doOnSubscribe {
                commits.load()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(commits)
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}