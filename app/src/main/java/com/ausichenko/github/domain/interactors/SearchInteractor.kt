package com.ausichenko.github.domain.interactors

import com.ausichenko.github.data.network.models.*
import com.ausichenko.github.domain.repository.SearchRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SearchInteractor(private val repository: SearchRepository) {

    fun getRepositories(searchQuery: String): Single<GitResponse<Repository>> {
        return repository.getRepositories(searchQuery)
            .subscribeOn(Schedulers.io())
    }

    fun getCommits(searchQuery: String): Single<GitResponse<Commit>> {
        return repository.getCommits(searchQuery)
            .subscribeOn(Schedulers.io())
    }

    fun getIssues(searchQuery: String): Single<GitResponse<Issue>> {
        return repository.getIssues(searchQuery)
            .subscribeOn(Schedulers.io())
    }

    fun getTopics(searchQuery: String): Single<GitResponse<Topic>> {
        return repository.getTopics(searchQuery)
            .subscribeOn(Schedulers.io())
    }

    fun getUsers(searchQuery: String): Single<GitResponse<User>> {
        return repository.getUsers(searchQuery)
            .subscribeOn(Schedulers.io())
    }
}