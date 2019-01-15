package com.ausichenko.github.domain.repository

import com.ausichenko.github.data.models.*
import io.reactivex.Completable
import io.reactivex.Observable

interface SearchRepository {

    fun getSearchHistory(): Observable<List<String>>
    fun saveSearchHistory(searchHistory: String): Completable

    fun getRepositories(searchQuery: String, page: Int, perPage: Int): Observable<List<Repository>>
    fun getCommits(searchQuery: String, page: Int, perPage: Int): Observable<List<Commit>>
    fun getIssues(searchQuery: String, page: Int, perPage: Int): Observable<List<Issue>>
    fun getTopics(searchQuery: String, page: Int, perPage: Int): Observable<List<Topic>>
    fun getUsers(searchQuery: String, page: Int, perPage: Int): Observable<List<User>>
}