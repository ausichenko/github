package com.ausichenko.github.data.repository

import com.ausichenko.github.data.datasource.LocalDataSource
import com.ausichenko.github.data.datasource.RemoteDataSource
import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.data.models.Topic
import com.ausichenko.github.data.models.User
import com.ausichenko.github.data.models.Commit
import com.ausichenko.github.data.models.Issue
import com.ausichenko.github.domain.repository.SearchRepository
import com.ausichenko.github.utils.mapper.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class SearchDataRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : SearchRepository {

    override fun getRepositories(searchQuery: String): Observable<List<Repository>> {
        return remoteDataSource.getRepositories(searchQuery)
            .map { it.items }
            .flattenAsObservable { it }
            .map { it.toRepository() }
            .toList()
            .toObservable()
            .subscribeOn(Schedulers.io())
    }

    override fun getCommits(searchQuery: String): Observable<List<Commit>> {
        return remoteDataSource.getCommits(searchQuery)
            .map { it.items }
            .flattenAsObservable { it }
            .map { it.toCommit() }
            .toList()
            .toObservable()
            .subscribeOn(Schedulers.io())
    }

    override fun getIssues(searchQuery: String): Observable<List<Issue>> {
        return remoteDataSource.getIssues(searchQuery)
            .map { it.items }
            .flattenAsObservable { it }
            .map { it.toIssue() }
            .toList()
            .toObservable()
            .subscribeOn(Schedulers.io())
    }

    override fun getTopics(searchQuery: String): Observable<List<Topic>> {
        return remoteDataSource.getTopics(searchQuery)
            .map { it.items }
            .flattenAsObservable { it }
            .map { it.toTopic() }
            .toList()
            .toObservable()
            .subscribeOn(Schedulers.io())
    }

    override fun getUsers(searchQuery: String): Observable<List<User>> {
        return remoteDataSource.getUsers(searchQuery)
            .map { it.items }
            .flattenAsObservable { it }
            .map { it.toUser() }
            .toList()
            .toObservable()
            .subscribeOn(Schedulers.io())
    }
}