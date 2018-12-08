package com.ausichenko.github.domain.repository

import com.ausichenko.github.data.network.models.GitCommit
import com.ausichenko.github.data.network.models.GitIssue
import com.ausichenko.github.data.network.models.GitRepository
import com.ausichenko.github.data.network.models.GitResponse
import io.reactivex.Single

interface SearchRepository {
    fun getRepositories(searchQuery: String): Single<GitResponse<GitRepository>>
    fun getCommits(searchQuery: String): Single<GitResponse<GitCommit>>
    fun getCode(): Single<List<Any>> // todo: You must authenticate to search for code across all public repositories.
    fun getIssues(searchQuery: String): Single<GitResponse<GitIssue>>
    fun getUsers(): Single<List<Any>>
    fun getTopics(): Single<List<Any>>
    fun getLabels(): Single<List<Any>>
}