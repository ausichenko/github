package com.ausichenko.github.data.network

import com.ausichenko.github.data.network.models.GitUser
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("users")
    fun getUsers(@Query("since") userId: Int?): Single<List<GitUser>>
}