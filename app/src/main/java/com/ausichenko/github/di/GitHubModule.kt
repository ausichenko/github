package com.ausichenko.github.di

import com.ausichenko.github.data.database.dao.SearchHistoryDao
import com.ausichenko.github.data.datasource.LocalDataSource
import com.ausichenko.github.data.datasource.RemoteDataSource
import com.ausichenko.github.data.network.GithubApi
import com.ausichenko.github.data.repository.SearchDataRepository
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.domain.repository.SearchRepository
import com.ausichenko.github.view.search.SearchViewModel
import com.ausichenko.github.view.search.commits.CommitsViewModel
import com.ausichenko.github.view.search.issues.IssuesViewModel
import com.ausichenko.github.view.search.repositories.RepositoriesViewModel
import com.ausichenko.github.view.search.topics.TopicsViewModel
import com.ausichenko.github.view.search.users.UsersViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val githubModule = module {
    single { makeLocalDataSource(get()) }
    single { makeRemoteDataSource(get()) }

    single { makeSearchRepository(get(), get()) }
    single { makeSearchInteractor(get()) }

    viewModel { SearchViewModel(get()) }
    viewModel { RepositoriesViewModel(get()) }
    viewModel { CommitsViewModel(get()) }
    viewModel { IssuesViewModel(get()) }
    viewModel { TopicsViewModel(get()) }
    viewModel { UsersViewModel(get()) }
}

fun makeLocalDataSource(searchHistoryDao: SearchHistoryDao): LocalDataSource {
    return LocalDataSource(searchHistoryDao)
}

fun makeRemoteDataSource(githubApi: GithubApi): RemoteDataSource {
    return RemoteDataSource(githubApi)
}

fun makeSearchRepository(
    localDataSource: LocalDataSource,
    remoteDataSource: RemoteDataSource
): SearchRepository {
    return SearchDataRepository(localDataSource, remoteDataSource)
}

fun makeSearchInteractor(searchRepository: SearchRepository): SearchInteractor {
    return SearchInteractor(searchRepository)
}