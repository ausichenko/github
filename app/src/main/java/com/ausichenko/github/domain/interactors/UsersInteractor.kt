package com.ausichenko.github.domain.interactors

import com.ausichenko.github.data.network.models.User
import com.ausichenko.github.domain.repository.UsersRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class UsersInteractor(private val repository: UsersRepository) {
    fun getUsers(): Single<List<User>> {
        return repository.getUsers()
            .subscribeOn(Schedulers.io())
    }
}