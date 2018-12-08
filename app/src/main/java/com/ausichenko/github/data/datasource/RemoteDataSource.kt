package com.ausichenko.github.data.datasource

import com.ausichenko.github.data.network.GithubApi
import com.ausichenko.github.data.network.models.*
import io.reactivex.Single

class RemoteDataSource(private val githubApi: GithubApi) : DataSource {

    // Search
    fun getRepositories(searchQuery: String): Single<GitResponse<GitRepository>> {
        return githubApi.getRepositories(searchQuery)
    }

    fun getCommits(searchQuery: String): Single<GitResponse<GitCommit>> {
        return githubApi.getCommits(searchQuery)
    }

    fun getIssues(searchQuery: String): Single<GitResponse<GitIssue>> {
        return githubApi.getIssues(searchQuery)
    }

    // Users
    override fun getUsers(): Single<List<GitUser>> {
        return githubApi.getUsers()
    }
}