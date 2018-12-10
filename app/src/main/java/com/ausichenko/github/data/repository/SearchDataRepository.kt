package com.ausichenko.github.data.repository

import com.ausichenko.github.data.datasource.LocalDataSource
import com.ausichenko.github.data.datasource.RemoteDataSource
import com.ausichenko.github.data.network.models.*
import com.ausichenko.github.domain.repository.SearchRepository
import io.reactivex.Single

class SearchDataRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : SearchRepository {

    override fun getRepositories(searchQuery: String): Single<Response<Repository>> {
        return remoteDataSource.getRepositories(searchQuery)
    }

    override fun getCommits(searchQuery: String): Single<Response<Commit>> {
        return remoteDataSource.getCommits(searchQuery)
    }

    override fun getCode(): Single<List<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getIssues(searchQuery: String): Single<Response<Issue>> {
        return remoteDataSource.getIssues(searchQuery)
    }

    override fun getTopics(searchQuery: String): Single<Response<Topic>> {
        return remoteDataSource.getTopics(searchQuery)
    }

    override fun getUsers(): Single<List<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLabels(): Single<List<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}