package com.ausichenko.github.data.repository

import com.ausichenko.github.data.network.NetworkDataSource
import com.ausichenko.github.data.network.models.GitUser
import com.ausichenko.github.domain.repository.UsersRepository
import io.reactivex.Single

class UsersDataRepository(private val networkDataSource: NetworkDataSource) : UsersRepository {

    override fun getUsers(): Single<List<GitUser>> {
        return networkDataSource.getUsers()
    }
}