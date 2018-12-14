package com.ausichenko.github.data.datasource

import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.data.network.GithubApi
import com.ausichenko.github.data.network.models.*
import com.ausichenko.github.utils.ext.checkResult
import io.reactivex.Observable
import io.reactivex.Single

class RemoteDataSource(private val githubApi: GithubApi) : DataSource {

    // Search
    fun getRepositories(searchQuery: String): Observable<GitResponse<Repository>> {
        return githubApi.getRepositories(searchQuery)
            .map {
                it.checkResult()
                return@map it.body()
            }
    }

    fun getCommits(searchQuery: String): Single<GitResponse<Commit>> {
        return githubApi.getCommits(searchQuery)
            .map {
                it.checkResult()
                return@map it.body()
            }
    }

    fun getIssues(searchQuery: String): Single<GitResponse<Issue>> {
        return githubApi.getIssues(searchQuery)
            .map {
                it.checkResult()
                return@map it.body()
            }
    }

    fun getTopics(searchQuery: String): Single<GitResponse<Topic>> {
        return githubApi.getTopics(searchQuery)
            .map {
                it.checkResult()
                return@map it.body()
            }
    }

    fun getUsers(searchQuery: String): Single<GitResponse<User>> {
        return githubApi.getUsers(searchQuery)
            .map {
                it.checkResult()
                return@map it.body()
            }
    }
}