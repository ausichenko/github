package com.ausichenko.github.data.repository

import com.ausichenko.github.data.database.models.SearchHistoryDB
import com.ausichenko.github.data.datasource.LocalDataSource
import com.ausichenko.github.data.datasource.RemoteDataSource
import com.ausichenko.github.data.models.*
import com.ausichenko.github.domain.repository.SearchRepository
import com.ausichenko.github.utils.mapper.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class SearchDataRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : SearchRepository {

    override fun getSearchHistory(): Observable<List<String>> {
        return localDataSource.getSearchHistory()
            .flattenAsObservable { it }
            .map { it.searchQuery }
            .toList()
            .toObservable()
            .subscribeOn(Schedulers.computation())
    }

    override fun saveSearchHistory(searchHistory: String): Completable {
        return Completable.fromAction {
            localDataSource.saveSearchHistory(SearchHistoryDB(searchHistory))
        }
            .subscribeOn(Schedulers.computation())
    }

    override fun getRepositories(
        searchQuery: String,
        page: Int,
        perPage: Int
    ): Observable<List<Repository>> {
        return remoteDataSource.getRepositories(searchQuery, page, perPage)
            .map { it.items }
            .flattenAsObservable { it }
            .map { it.toRepository() }
            .toList()
            .toObservable()
            .subscribeOn(Schedulers.io())
    }

    override fun getCommits(
        searchQuery: String,
        page: Int,
        perPage: Int
    ): Observable<List<Commit>> {
        return remoteDataSource.getCommits(searchQuery, page, perPage)
            .map { it.items }
            .flattenAsObservable { it }
            .map { it.toCommit() }
            .toList()
            .toObservable()
            .subscribeOn(Schedulers.io())
    }

    override fun getIssues(
        searchQuery: String,
        page: Int,
        perPage: Int
    ): Observable<List<Issue>> {
        return remoteDataSource.getIssues(searchQuery, page, perPage)
            .map { it.items }
            .flattenAsObservable { it }
            .map { it.toIssue() }
            .toList()
            .toObservable()
            .subscribeOn(Schedulers.io())
    }

    override fun getTopics(
        searchQuery: String,
        page: Int,
        perPage: Int
    ): Observable<List<Topic>> {
        return remoteDataSource.getTopics(searchQuery, page, perPage)
            .map { it.items }
            .flattenAsObservable { it }
            .map { it.toTopic() }
            .toList()
            .toObservable()
            .subscribeOn(Schedulers.io())
    }

    override fun getUsers(
        searchQuery: String,
        page: Int,
        perPage: Int
    ): Observable<List<User>> {
        return remoteDataSource.getUsers(searchQuery, page, perPage)
            .map { it.items }
            .flattenAsObservable { it }
            .map { it.toUser() }
            .toList()
            .toObservable()
            .subscribeOn(Schedulers.io())
    }
}