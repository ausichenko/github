package com.ausichenko.github.data.repository

import com.ausichenko.github.data.network.NetworkDataSource
import com.ausichenko.github.data.network.models.GitUser
import com.ausichenko.github.domain.repository.UsersRepository
import retrofit2.Call

class UsersDataRepository(private val networkDataSource: NetworkDataSource) : UsersRepository {

    override fun getUsers(): Call<List<GitUser>> {
        return networkDataSource.getUsers()
    }
}