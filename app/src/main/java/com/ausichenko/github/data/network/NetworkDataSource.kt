package com.ausichenko.github.data.network

import com.ausichenko.github.data.network.models.GitUser
import retrofit2.Call

class NetworkDataSource(private val githubApi: GithubApi) {
    fun getUsers(): Call<List<GitUser>> {
        return githubApi.getUsers(null)
    }
}