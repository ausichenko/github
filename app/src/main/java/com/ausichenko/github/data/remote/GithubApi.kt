package com.ausichenko.github.data.remote

import com.ausichenko.github.data.remote.models.GitUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("users")
    fun getUsers(@Query("since") userId: Int) : Call<List<GitUser>>
}