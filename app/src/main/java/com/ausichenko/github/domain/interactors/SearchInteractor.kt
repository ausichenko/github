package com.ausichenko.github.domain.interactors

import com.ausichenko.github.data.network.models.GitCommit
import com.ausichenko.github.data.network.models.GitRepository
import com.ausichenko.github.data.network.models.GitResponse
import com.ausichenko.github.domain.repository.SearchRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SearchInteractor(private val repository: SearchRepository) {

    fun getRepositories(searchQuery: String): Single<GitResponse<GitRepository>> {
        return repository.getRepositories(searchQuery)
            .subscribeOn(Schedulers.io())
    }

    fun getCommits(searchQuery: String): Single<GitResponse<GitCommit>> {
        return repository.getCommits(searchQuery)
            .subscribeOn(Schedulers.io())
    }
}