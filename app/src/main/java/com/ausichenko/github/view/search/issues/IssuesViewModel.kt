package com.ausichenko.github.view.search.issues

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.models.Issue
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.ObserverLiveData
import com.ausichenko.github.utils.livedata.isError
import com.ausichenko.github.utils.livedata.isLoading
import io.reactivex.android.schedulers.AndroidSchedulers

class IssuesViewModel(private val interactor: SearchInteractor) : ViewModel() {

    var issues: ObserverLiveData<List<Issue>> = ObserverLiveData()
    val isLoading: LiveData<Boolean> = issues.isLoading()
    val isError: LiveData<Boolean> = issues.isError()

    fun loadIssues(searchQueryLiveData: LiveData<String>) {
        val query = searchQueryLiveData.value.toString()
        loadIssues(query)
    }

    private fun loadIssues(searchQuery: String) {
        interactor.getIssues(searchQuery)
            .doOnSubscribe {
                issues.load()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(issues)
    }
}