package com.ausichenko.github.domain.repository

import com.ausichenko.github.data.network.models.GitRepository
import com.ausichenko.github.data.network.models.GitResponse
import io.reactivex.Single

interface SearchRepository {
    fun getRepositories(searchQuery: String): Single<GitResponse<GitRepository>>
    fun getCommits(): Single<List<Any>>
    fun getCode(): Single<List<Any>> // todo: You must authenticate to search for code across all public repositories.
    fun getIssues(): Single<List<Any>>
    fun getUsers(): Single<List<Any>>
    fun getTopics(): Single<List<Any>>
    fun getLabels(): Single<List<Any>>
}