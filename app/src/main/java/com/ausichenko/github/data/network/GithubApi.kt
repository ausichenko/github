package com.ausichenko.github.data.network

import com.ausichenko.github.data.network.models.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubApi {

    // Search
    @GET("search/repositories?sort=stars&order=desc")
    fun getRepositories(@Query("q") searchQuery: String): Single<Response<GitResponse<RepositoryNetwork>>>

    @Headers("Accept: application/vnd.github.cloak-preview")
    @GET("search/commits")
    fun getCommits(@Query("q") searchQuery: String): Single<Response<GitResponse<Commit>>>

    @GET("search/issues")
    fun getIssues(@Query("q") searchQuery: String): Single<Response<GitResponse<Issue>>>

    @Headers("Accept: application/vnd.github.mercy-preview+json")
    @GET("search/topics")
    fun getTopics(@Query("q") searchQuery: String): Single<Response<GitResponse<Topic>>>

    @GET("search/users")
    fun getUsers(@Query("q") searchQuery: String): Single<Response<GitResponse<User>>>
}