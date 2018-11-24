package com.ausichenko.github.data.datasource

import com.ausichenko.github.data.network.GithubApi
import com.ausichenko.github.data.network.models.GitUser
import io.reactivex.Single

class RemoteDataSource(private val githubApi: GithubApi): DataSource {

    // Search
    fun getRepositories(searchQuery: String): Single<List<Any>> {
        return githubApi.getRepositories(searchQuery)
    }

    // Users
    override fun getUsers(): Single<List<GitUser>> {
        return githubApi.getUsers()
    }
}