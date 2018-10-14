package com.ausichenko.github.domain.interactors

import com.ausichenko.github.data.network.models.GitUser
import com.ausichenko.github.domain.repository.UsersRepository
import retrofit2.Call

class UsersInteractor(private val repository: UsersRepository) {
    fun getUsers(): Call<List<GitUser>> {
        return repository.getUsers()
    }
}