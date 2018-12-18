package com.ausichenko.github.domain.interactors

import com.ausichenko.github.data.exceptions.StartSearchException
import com.ausichenko.github.data.models.*
import com.ausichenko.github.domain.repository.SearchRepository
import io.reactivex.Completable
import io.reactivex.Observable

class SearchInteractor(private val repository: SearchRepository) {

    fun getSearchHistory(): Observable<List<String>> {
        return repository.getSearchHistory()
    }

    fun saveSearchHistory(searchHistory: String): Completable {
        return repository.saveSearchHistory(searchHistory)
    }

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