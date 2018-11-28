package com.ausichenko.github.data.network

import com.ausichenko.github.data.network.models.GitRepository
import com.ausichenko.github.data.network.models.GitResponse
import com.ausichenko.github.data.network.models.GitUser
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    // Search
    @GET("search/repositories")
    fun getRepositories(@Query("q") searchQuery: String): Single<GitResponse<GitRepository>>

    // Users
    @GET("users")
    fun getUsers(@Query("since") userId: Int? = null): Single<List<GitUser>>
}