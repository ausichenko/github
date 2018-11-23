package com.ausichenko.github.di

import com.ausichenko.github.data.datasource.LocalDataSource
import com.ausichenko.github.data.network.GithubApi
import com.ausichenko.github.data.datasource.RemoteDataSource
import com.ausichenko.github.data.repository.SearchDataRepository
import com.ausichenko.github.data.repository.UsersDataRepository
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.domain.interactors.UsersInteractor
import com.ausichenko.github.domain.repository.SearchRepository
import com.ausichenko.github.domain.repository.UsersRepository
import com.ausichenko.github.view.main.users.UsersViewModel
import com.ausichenko.github.view.search.SearchViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val githubModule = module {
    single { makeNetworkDataSource(get()) }

    single { makeSearchRepository(get()) }
    single { makeSearchInteractor(get()) }
    viewModel { SearchViewModel(get()) }

    single { makeUsersRepository(get()) }
    single { makeUsersInteractor(get()) }
    viewModel { UsersViewModel(get()) }
}

fun makeNetworkDataSource(githubApi: GithubApi): RemoteDataSource {
    return RemoteDataSource(githubApi)
}

fun makeSearchRepository(remoteDataSource: RemoteDataSource): SearchRepository {
    return SearchDataRepository(LocalDataSource(), remoteDataSource)
}

fun makeSearchInteractor(searchRepository: SearchRepository): SearchInteractor {
    return SearchInteractor(searchRepository)
}

fun makeUsersRepository(remoteDataSource: RemoteDataSource): UsersRepository {
    return UsersDataRepository(LocalDataSource(), remoteDataSource)
}

fun makeUsersInteractor(usersRepository: UsersRepository): UsersInteractor {
    return UsersInteractor(usersRepository)
}