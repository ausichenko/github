package com.ausichenko.github.data.datasource

import com.ausichenko.github.data.network.GithubApi
import com.ausichenko.github.data.network.models.*
import io.reactivex.Single

class RemoteDataSource(private val githubApi: GithubApi) : DataSource {

    // Search
    fun getRepositories(searchQuery: String): Single<Response<Repository>> {
        return githubApi.getRepositories(searchQuery)
    }

    fun getCommits(searchQuery: String): Single<Response<Commit>> {
        return githubApi.getCommits(searchQuery)
    }

    fun getIssues(searchQuery: String): Single<Response<Issue>> {
        return githubApi.getIssues(searchQuery)
    }

    // Users
    override fun getUsers(): Single<List<User>> {
        return githubApi.getUsers()
    }
}