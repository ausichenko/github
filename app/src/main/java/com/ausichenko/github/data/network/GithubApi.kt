package com.ausichenko.github.data.network

import com.ausichenko.github.data.network.models.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    // Search
    @GET("search/repositories")
    fun getRepositories(@Query("q") searchQuery: String): Single<Response<Repository>>

    @GET("search/commits")
    fun getCommits(@Query("q") searchQuery: String): Single<Response<Commit>>

    @GET("search/issues")
    fun getIssues(@Query("q") searchQuery: String): Single<Response<Issue>>

    // Users
    @GET("users")
    fun getUsers(@Query("since") userId: Int? = null): Single<List<User>>
}