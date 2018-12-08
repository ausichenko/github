package com.ausichenko.github.view.search.issues

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.network.models.GitIssue
import com.ausichenko.github.data.network.models.GitResponse
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class IssuesViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private val disposable = CompositeDisposable()

    var issues: ObserverLiveData<GitResponse<GitIssue>, Throwable> = ObserverLiveData()
    val isSuccess: LiveData<Boolean> = issues.isSuccess()
    val isLoading: LiveData<Boolean> = issues.isLoading()
    val isError: LiveData<Boolean> = issues.isError()
    val isEmpty: LiveData<Boolean> = issues.isEmpty()

    fun loadIssues(searchQueryLiveData: LiveData<String>) {
        val query = searchQueryLiveData.value.toString()
        loadIssues(query)
    }

    private fun loadIssues(searchQuery: String) {
        disposable.add(interactor.getIssues(searchQuery)
            .doOnSubscribe {
                issues.load()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(issues)
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}