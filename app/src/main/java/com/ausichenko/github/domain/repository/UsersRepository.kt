package com.ausichenko.github.domain.repository

import com.ausichenko.github.data.network.models.User
import io.reactivex.Single

interface UsersRepository {
    fun getUsers(): Single<List<User>>
}