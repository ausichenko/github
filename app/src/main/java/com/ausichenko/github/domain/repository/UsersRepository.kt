package com.ausichenko.github.domain.repository

import com.ausichenko.github.data.network.models.GitUser
import retrofit2.Call

interface UsersRepository {
    fun getUsers(): Call<List<GitUser>>
}