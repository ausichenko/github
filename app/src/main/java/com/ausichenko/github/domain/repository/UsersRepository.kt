package com.ausichenko.github.domain.repository

import com.ausichenko.github.data.network.models.GitUser
import io.reactivex.Single

interface UsersRepository {
    fun getUsers(): Single<List<GitUser>>
}