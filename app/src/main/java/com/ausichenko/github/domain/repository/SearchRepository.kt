package com.ausichenko.github.domain.repository

import com.ausichenko.github.data.network.models.*
import io.reactivex.Single

interface SearchRepository {
    fun getRepositories(searchQuery: String): Single<Response<Repository>>
    fun getCommits(searchQuery: String): Single<Response<Commit>>
    fun getCode(): Single<List<Any>> // todo: You must authenticate to search for code across all public repositories.
    fun getIssues(searchQuery: String): Single<Response<Issue>>
    fun getTopics(searchQuery: String): Single<Response<Topic>>
    fun getUsers(): Single<List<Any>>
    fun getLabels(): Single<List<Any>>
}