package com.ausichenko.github.view.search.commits

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.models.Commit
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.ObserverLiveData
import io.reactivex.android.schedulers.AndroidSchedulers

class CommitsViewModel(private val interactor: SearchInteractor) : ViewModel() {

    var commits: ObserverLiveData<List<Commit>> = ObserverLiveData()

    fun loadCommits(searchQueryLiveData: LiveData<String>) {
        val query = searchQueryLiveData.value.toString()
        loadCommits(query)
    }

    private fun loadCommits(searchQuery: String) {
        interactor.getCommits(searchQuery)
            .doOnSubscribe {
                commits.load()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(commits)
    }
}