package com.ausichenko.github.view.search.repositories

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.StateLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class RepositoriesViewModel(private val interactor: SearchInteractor) : ViewModel() {

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
        interactor.getRepositories(currentSearchQuery, currentPage)
            .doOnSubscribe { disposable ->
                compositeDisposable.add(disposable)
                initialState.prepareLoading()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ repositories ->
                initialState.prepareSuccess(repositories)
            }, { error ->
                initialState.prepareError(error)
            })
    }

    @SuppressLint("CheckResult")
    fun loadMore() {
        if (initialState.isLoading() || pagedState.isLoading() || hasLoadedAllItems)
            return

        currentPage++
        interactor.getRepositories(currentSearchQuery, currentPage)
            .doOnSubscribe { disposable ->
                compositeDisposable.add(disposable)
                pagedState.prepareLoading()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ repositories ->
                pagedState.prepareSuccess(repositories)
                if (repositories.isEmpty())
                    hasLoadedAllItems = true
            }, { error ->
                pagedState.prepareError(error)
            })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}