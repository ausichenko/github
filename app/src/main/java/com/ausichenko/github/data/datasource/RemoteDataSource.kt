package com.ausichenko.github.data.datasource

import com.ausichenko.github.data.network.GithubApi
import com.ausichenko.github.data.network.models.GitCommit
import com.ausichenko.github.data.network.models.GitRepository
import com.ausichenko.github.data.network.models.GitResponse
import com.ausichenko.github.data.network.models.GitUser
import io.reactivex.Single

class RemoteDataSource(private val githubApi: GithubApi): DataSource {

    // Search
    fun getRepositories(searchQuery: String): Single<GitResponse<GitRepository>> {
        return githubApi.getRepositories(searchQuery)
    }

    fun getCommits(searchQuery: String): Single<GitResponse<GitCommit>> {
        return githubApi.getCommits(searchQuery)
    }

    // Users
    override fun getUsers(): Single<List<GitUser>> {
        return githubApi.getUsers()
    }
}