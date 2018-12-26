package com.ausichenko.github.data.datasource

import com.ausichenko.github.data.network.GithubApi
import com.ausichenko.github.data.network.models.*
import com.ausichenko.github.utils.ext.checkResult
import io.reactivex.Single

class RemoteDataSource(private val githubApi: GithubApi) : DataSource {

    fun getRepositories(
        searchQuery: String,
        page: Int,
        perPage: Int
    ): Single<GitResponse<RepositoryNetwork>> {
        return githubApi.getRepositories(searchQuery, page, perPage)
            .checkResult()
    }

    fun getCommits(searchQuery: String): Single<GitResponse<CommitNetwork>> {
        return githubApi.getCommits(searchQuery)
            .checkResult()
    }

    fun getIssues(searchQuery: String): Single<GitResponse<IssueNetwork>> {
        return githubApi.getIssues(searchQuery)
            .checkResult()
    }

    fun getTopics(searchQuery: String): Single<GitResponse<TopicNetwork>> {
        return githubApi.getTopics(searchQuery)
            .checkResult()
    }

    fun getUsers(searchQuery: String): Single<GitResponse<UserNetwork>> {
        return githubApi.getUsers(searchQuery)
            .checkResult()
    }
}