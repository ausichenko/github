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

    fun getRepositories(
        searchQuery: String,
        page: Int,
        perPage: Int
    ): Observable<List<Repository>> {
        return Observable.just(searchQuery)
            .filter { it.isNotEmpty() }
            .switchIfEmpty { it.onError(StartSearchException()) }
            .switchMap { repository.getRepositories(it, page, perPage) }
    }

    fun getCommits(
        searchQuery: String,
        page: Int,
        perPage: Int
    ): Observable<List<Commit>> {
        return Observable.just(searchQuery)
            .filter { it.isNotEmpty() }
            .switchIfEmpty { it.onError(StartSearchException()) }
            .switchMap { repository.getCommits(it, page, perPage) }
    }

    fun getIssues(
        searchQuery: String,
        page: Int,
        perPage: Int
    ): Observable<List<Issue>> {
        return Observable.just(searchQuery)
            .filter { it.isNotEmpty() }
            .switchIfEmpty { it.onError(StartSearchException()) }
            .switchMap { repository.getIssues(it, page, perPage) }
    }

    fun getTopics(
        searchQuery: String,
        page: Int,
        perPage: Int
    ): Observable<List<Topic>> {
        return Observable.just(searchQuery)
            .filter { it.isNotEmpty() }
            .switchIfEmpty { it.onError(StartSearchException()) }
            .switchMap { repository.getTopics(it, page, perPage) }
    }

    fun getUsers(
        searchQuery: String,
        page: Int,
        perPage: Int
    ): Observable<List<User>> {
        return Observable.just(searchQuery)
            .filter { it.isNotEmpty() }
            .switchIfEmpty { it.onError(StartSearchException()) }
            .switchMap { repository.getUsers(it, page, perPage) }
    }
}