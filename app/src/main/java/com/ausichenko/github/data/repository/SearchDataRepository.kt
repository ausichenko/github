package com.ausichenko.github.data.repository

import com.ausichenko.github.data.database.models.RepositoryDB
import com.ausichenko.github.data.datasource.LocalDataSource
import com.ausichenko.github.data.datasource.RemoteDataSource
import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.data.network.models.*
import com.ausichenko.github.domain.repository.SearchRepository
import com.ausichenko.github.utils.mapper.toRepository
import com.ausichenko.github.utils.mapper.toRepositoryDB
import io.reactivex.*
import io.reactivex.schedulers.Schedulers

class SearchDataRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : SearchRepository {

    override fun getRepositories(searchQuery: String): Observable<List<Repository>> {

        val local = localDataSource.getRepositories(searchQuery)
            .flattenAsObservable { it }
            .map { it.toRepository() }
            .toList()
            .filter { it.isNotEmpty() }
            .subscribeOn(Schedulers.computation())

        val remote = remoteDataSource.getRepositories(searchQuery)
            .map { it.items }
            .doAfterSuccess { items ->
                saveRepositories(items, searchQuery)
            }
            .flattenAsObservable { it }
            .map { it.toRepository() }
            .toList()
            .subscribeOn(Schedulers.io())

        return Maybe.concat(local, remote.toMaybe()).toObservable()
    }

    private fun saveRepositories(networkRepositories: List<RepositoryNetwork>, searchQuery: String) {
        val databaseRepositories = ArrayList<RepositoryDB>()
        networkRepositories.forEach {
            databaseRepositories.add(it.toRepositoryDB(searchQuery))
        }
        Completable.fromCallable { localDataSource.saveRepositories(databaseRepositories) }
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .subscribe()
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