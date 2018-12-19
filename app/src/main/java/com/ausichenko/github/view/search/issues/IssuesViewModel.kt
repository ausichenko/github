package com.ausichenko.github.view.search.issues

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.models.Issue
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.ObserverLiveData
import io.reactivex.android.schedulers.AndroidSchedulers

class IssuesViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private lateinit var issues: ObserverLiveData<List<Issue>>

    fun getIssues(searchQueryLiveData: LiveData<String>): ObserverLiveData<List<Issue>> {
        if (!::issues.isInitialized) {
            issues = ObserverLiveData()
            loadIssues(searchQueryLiveData)
        }
        return issues
    }

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