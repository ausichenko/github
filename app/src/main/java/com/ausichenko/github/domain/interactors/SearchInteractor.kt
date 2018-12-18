package com.ausichenko.github.domain.interactors

import com.ausichenko.github.data.exceptions.StartSearchException
import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.data.models.User
import com.ausichenko.github.data.models.Commit
import com.ausichenko.github.data.models.Issue
import com.ausichenko.github.data.models.Topic
import com.ausichenko.github.domain.repository.SearchRepository
import io.reactivex.Observable

class SearchInteractor(private val repository: SearchRepository) {

    fun getRepositories(searchQuery: String): Observable<List<Repository>> {
        return Observable.just(searchQuery)
            .filter { it.isNotEmpty() }
            .switchIfEmpty { it.onError(StartSearchException()) }
            .switchMap { repository.getRepositories(it) }
    }

    fun getCommits(searchQuery: String): Observable<List<Commit>> {
        return Observable.just(searchQuery)
            .filter { it.isNotEmpty() }
            .switchIfEmpty { it.onError(StartSearchException()) }
            .switchMap { repository.getCommits(it) }
    }

    fun getIssues(searchQuery: String): Observable<List<Issue>> {
        return Observable.just(searchQuery)
            .filter { it.isNotEmpty() }
            .switchIfEmpty { it.onError(StartSearchException()) }
            .switchMap { repository.getIssues(it) }
    }

    fun getTopics(searchQuery: String): Observable<List<Topic>> {
        return Observable.just(searchQuery)
            .filter { it.isNotEmpty() }
            .switchIfEmpty { it.onError(StartSearchException()) }
            .switchMap { repository.getTopics(it) }
    }

    fun getUsers(searchQuery: String): Observable<List<User>> {
        return Observable.just(searchQuery)
            .filter { it.isNotEmpty() }
            .switchIfEmpty { it.onError(StartSearchException()) }
            .switchMap { repository.getUsers(it) }
    }
}