package com.ausichenko.github.view.search.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class RepositoriesViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private val disposable = CompositeDisposable()

    var repositories: ObserverLiveData<List<Any>, Throwable> = ObserverLiveData()
    val isSuccess: LiveData<Boolean> = repositories.isSuccess()
    val isLoading: LiveData<Boolean> = repositories.isLoading()
    val isError: LiveData<Boolean> = repositories.isError()
    val isEmpty: LiveData<Boolean> = repositories.isEmpty()

    var testSearchQuery = MutableLiveData<String>()

    fun loadRepositories(searchQuery: LiveData<String>) {
        val query = searchQuery.value.toString()
        loadRepositories(query)
    }

    private fun loadRepositories(query: String) {
        disposable.add(interactor.getRepositories()
                .doOnSubscribe {
                    repositories.load()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repositories)
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}