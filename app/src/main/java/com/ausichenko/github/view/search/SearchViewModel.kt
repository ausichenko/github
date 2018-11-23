package com.ausichenko.github.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.domain.interactors.UsersInteractor
import com.ausichenko.github.utils.livedata.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class SearchViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private val disposable = CompositeDisposable()

    var repositories: ObserverLiveData<List<Any>, Throwable> = ObserverLiveData()
    val isSuccess: LiveData<Boolean> = repositories.isSuccess()
    val isLoading: LiveData<Boolean> = repositories.isLoading()
    val isError: LiveData<Boolean> = repositories.isError()
    val isEmpty: LiveData<Boolean> = repositories.isEmpty()

    fun loadRepositories() {
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