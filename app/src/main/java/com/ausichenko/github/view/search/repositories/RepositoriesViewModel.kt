package com.ausichenko.github.view.search.repositories

import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.ObserverLiveData
import io.reactivex.android.schedulers.AndroidSchedulers

class RepositoriesViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private lateinit var repositories: ObserverLiveData<List<Repository>>

    fun getRepositories(searchQuery: String): ObserverLiveData<List<Repository>> {
        if (!::repositories.isInitialized) {
            repositories = ObserverLiveData()
            loadRepositories(searchQuery)
        }
        return repositories
    }

    fun loadRepositories(searchQuery: String) {
        interactor.getRepositories(searchQuery)
            .doOnSubscribe {
                repositories.load()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(repositories)
    }
}