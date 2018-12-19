package com.ausichenko.github.view.search.commits

import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.models.Commit
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.ObserverLiveData
import io.reactivex.android.schedulers.AndroidSchedulers

class CommitsViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private lateinit var commits: ObserverLiveData<List<Commit>>

    fun getCommits(searchQuery: String): ObserverLiveData<List<Commit>> {
        if (!::commits.isInitialized) {
            commits = ObserverLiveData()
            loadCommits(searchQuery)
        }
        return commits
    }

    fun loadCommits(searchQuery: String) {
        interactor.getCommits(searchQuery)
            .doOnSubscribe {
                commits.load()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(commits)
    }
}