package com.ausichenko.github.data.network

import com.ausichenko.github.data.network.models.GitUser
import io.reactivex.Single

class NetworkDataSource(private val githubApi: GithubApi) {
    fun getUsers(): Single<List<GitUser>> {
        return githubApi.getUsers(null)
    }
}