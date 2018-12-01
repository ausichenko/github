package com.ausichenko.github.data.repository

import com.ausichenko.github.data.datasource.LocalDataSource
import com.ausichenko.github.data.datasource.RemoteDataSource
import com.ausichenko.github.data.network.models.GitUser
import com.ausichenko.github.domain.repository.UsersRepository
import io.reactivex.Single

class UsersDataRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : UsersRepository {

    override fun getUsers(): Single<List<GitUser>> {
        return localDataSource.isEmpty().flatMap { empty ->
            if (!empty) {
                return@flatMap localDataSource.getUsers()
            } else {
                return@flatMap remoteDataSource.getUsers()
                    .doOnSuccess { users ->
                        localDataSource.saveAll(users)
                    }
            }
        }
    }
}