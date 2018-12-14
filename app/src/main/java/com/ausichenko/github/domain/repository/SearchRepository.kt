package com.ausichenko.github.domain.repository

import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.data.network.models.*
import io.reactivex.Observable
import io.reactivex.Single

interface SearchRepository {
    fun getRepositories(searchQuery: String): Observable<List<Repository>>
    fun getCommits(searchQuery: String): Single<GitResponse<Commit>>
    fun getCode(): Single<List<Any>> // todo: You must authenticate to search for code across all public repositories.
    fun getIssues(searchQuery: String): Single<GitResponse<Issue>>
    fun getTopics(searchQuery: String): Single<GitResponse<Topic>>
    fun getUsers(searchQuery: String): Single<GitResponse<User>>
    fun getLabels(): Single<List<Any>>
}