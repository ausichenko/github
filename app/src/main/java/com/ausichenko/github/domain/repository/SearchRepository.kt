package com.ausichenko.github.domain.repository

import com.ausichenko.github.data.models.*
import io.reactivex.Completable
import io.reactivex.Observable

interface SearchRepository {

    fun getSearchHistory(): Observable<List<String>>
    fun saveSearchHistory(searchHistory: String): Completable

    fun getRepositories(searchQuery: String, page: Int): Observable<List<Repository>>
    fun getCommits(searchQuery: String): Observable<List<Commit>>
    fun getIssues(searchQuery: String): Observable<List<Issue>>
    fun getTopics(searchQuery: String): Observable<List<Topic>>
    fun getUsers(searchQuery: String): Observable<List<User>>
}