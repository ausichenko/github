package com.ausichenko.github.data.network

import com.ausichenko.github.data.network.models.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubApi {

    // Search
    @GET("search/repositories")
    fun getRepositories(@Query("q") searchQuery: String): Single<Response<Repository>>

    @Headers("Accept: application/vnd.github.cloak-preview")
    @GET("search/commits")
    fun getCommits(@Query("q") searchQuery: String): Single<Response<Commit>>

    @GET("search/issues")
    fun getIssues(@Query("q") searchQuery: String): Single<Response<Issue>>

    @Headers("Accept: application/vnd.github.mercy-preview+json")
    @GET("search/topics")
    fun getTopics(@Query("q") searchQuery: String): Single<Response<Topic>>

    @GET("search/users")
    fun getUsers(@Query("q") searchQuery: String): Single<Response<User>>
}