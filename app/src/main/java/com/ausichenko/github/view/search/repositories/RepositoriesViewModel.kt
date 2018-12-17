package com.ausichenko.github.view.search.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.*
import io.reactivex.android.schedulers.AndroidSchedulers

class RepositoriesViewModel(private val interactor: SearchInteractor) : ViewModel() {

    var repositories: ObserverLiveData<List<Repository>> = ObserverLiveData()

    fun loadRepositories(searchQueryLiveData: LiveData<String>) {
        val query = searchQueryLiveData.value.toString()
        loadRepositories(query)
    }

    private fun loadRepositories(searchQuery: String) {
        interactor.getRepositories(searchQuery)
            .doOnSubscribe {
                repositories.load()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(repositories)
    }
}