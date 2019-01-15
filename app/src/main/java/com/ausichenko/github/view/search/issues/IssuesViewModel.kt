package com.ausichenko.github.view.search.issues

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.models.Issue
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.StateLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class IssuesViewModel(private val interactor: SearchInteractor) : ViewModel() {

    companion object {
        private const val PER_PAGE = 30
    }

    private val compositeDisposable = CompositeDisposable()

    val initialState = StateLiveData<List<Issue>>()
    val pagedState = StateLiveData<List<Issue>>()

    private var currentSearchQuery = ""
    private var currentPage = 1
    private var hasLoadedAllItems = false

    @SuppressLint("CheckResult")
    fun loadIssues(searchQuery: String) {
        currentSearchQuery = searchQuery
        currentPage = 1
        hasLoadedAllItems = false

        interactor.getIssues(currentSearchQuery, currentPage, PER_PAGE)
            .doOnSubscribe { disposable ->
                compositeDisposable.add(disposable)
                initialState.prepareLoading()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ issues ->
                initialState.prepareSuccess(issues)
                if (issues.size < PER_PAGE) {
                    hasLoadedAllItems = true
                }
            }, { error ->
                initialState.prepareError(error)
                hasLoadedAllItems = true
            })
    }

    @SuppressLint("CheckResult")
    fun loadMore() {
        if (initialState.isLoading() || pagedState.isLoading() || hasLoadedAllItems)
            return

        currentPage++
        interactor.getIssues(currentSearchQuery, currentPage, PER_PAGE)
            .doOnSubscribe { disposable ->
                compositeDisposable.add(disposable)
                pagedState.prepareLoading()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ issues ->
                pagedState.prepareSuccess(issues)
                if (issues.size < PER_PAGE)
                    hasLoadedAllItems = true
            }, { error ->
                pagedState.prepareError(error)
                hasLoadedAllItems = true
            })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}