package com.ausichenko.github.data.repository

import com.ausichenko.github.data.datasource.LocalDataSource
import com.ausichenko.github.data.datasource.RemoteDataSource
import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.data.network.models.*
import com.ausichenko.github.domain.repository.SearchRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SearchDataRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : SearchRepository {

    override fun getRepositories(searchQuery: String): Observable<List<Repository>> {

        val local = localDataSource.getRepositories(searchQuery)
            .filter { list -> list.isNotEmpty() }
            .subscribeOn(Schedulers.computation())

        val remote = remoteDataSource.getRepositories(searchQuery)
            .map { it.items }
                /*
            .doOnNext { items ->
                Observable.create<Any> { subscriber ->
                    items.forEach { repository ->
                        repository.searchQuery = searchQuery
                    }
                    //localDataSource.saveRepositories(items)
                    subscriber.onComplete()
                }.subscribeOn(Schedulers.computation()).subscribe()
            }
            */
            .subscribeOn(Schedulers.io())

        return remote//Observable.concat(local, remote)
    }

    override fun getCommits(searchQuery: String): Single<GitResponse<Commit>> {
        return remoteDataSource.getCommits(searchQuery)
    }

    override fun getCode(): Single<List<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getIssues(searchQuery: String): Single<GitResponse<Issue>> {
        return remoteDataSource.getIssues(searchQuery)
    }

    override fun getTopics(searchQuery: String): Single<GitResponse<Topic>> {
        return remoteDataSource.getTopics(searchQuery)
    }

    override fun getUsers(searchQuery: String): Single<GitResponse<User>> {
        return remoteDataSource.getUsers(searchQuery)
    }

    override fun getLabels(): Single<List<Any>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}