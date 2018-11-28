package com.ausichenko.github.data.repository

import com.ausichenko.github.data.datasource.LocalDataSource
import com.ausichenko.github.data.datasource.RemoteDataSource
import com.ausichenko.github.data.network.models.GitRepository
import com.ausichenko.github.data.network.models.GitResponse
import com.ausichenko.github.domain.repository.SearchRepository
import io.reactivex.Single

class SearchDataRepository(private val localDataSource: LocalDataSource,
                           private val remoteDataSource: RemoteDataSource) : SearchRepository {

    override fun getRepositories(searchQuery: String): Single<GitResponse<GitRepository>> {
        return remoteDataSource.getRepositories(searchQuery)
    }

    override fun getCommits(): Single<List<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCode(): Single<List<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getIssues(): Single<List<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUsers(): Single<List<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTopics(): Single<List<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLabels(): Single<List<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}