package com.ausichenko.github.di

import com.ausichenko.github.data.network.GithubApi
import com.ausichenko.github.data.network.NetworkDataSource
import com.ausichenko.github.data.repository.UsersDataRepository
import com.ausichenko.github.domain.interactors.UsersInteractor
import com.ausichenko.github.view.main.users.UsersViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val githubModule = module {
    single { makeNetworkDataSource(get()) }
    single { makeUsersRepository(get()) }
    single { makeUsersInteractor(get()) }
    viewModel { UsersViewModel(get()) }
}

fun makeNetworkDataSource(githubApi: GithubApi): NetworkDataSource {
    return NetworkDataSource(githubApi)
}

fun makeUsersRepository(networkDataSource: NetworkDataSource): UsersDataRepository {
    return UsersDataRepository(networkDataSource)
}

fun makeUsersInteractor(usersRepository: UsersDataRepository): UsersInteractor {
    return  UsersInteractor(usersRepository)
}