package com.ausichenko.github.view.search.repositories

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.StateLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class RepositoriesViewModel(private val interactor: SearchInteractor) : ViewModel() {

    companion object {
        private const val PER_PAGE = 30
    }

    private val compositeDisposable = CompositeDisposable()

    val initialState = StateLiveData<List<Repository>>()
    val pagedState = StateLiveData<List<Repository>>()

    private var currentSearchQuery = ""
    private var currentPage = 1
    private var hasLoadedAllItems = false

    @SuppressLint("CheckResult")
    fun loadRepositories(searchQuery: String) {
        currentSearchQuery = searchQuery
        currentPage = 1
        hasLoadedAllItems = false

        interactor.getRepositories(currentSearchQuery, currentPage, PER_PAGE)
            .doOnSubscribe { disposable ->
                compositeDisposable.add(disposable)
                initialState.prepareLoading()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ repositories ->
                initialState.prepareSuccess(repositories)
                if (repositories.size < PER_PAGE) {
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
        interactor.getRepositories(currentSearchQuery, currentPage, PER_PAGE)
            .doOnSubscribe { disposable ->
                compositeDisposable.add(disposable)
                pagedState.prepareLoading()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ repositories ->
                pagedState.prepareSuccess(repositories)
                if (repositories.size < PER_PAGE)
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