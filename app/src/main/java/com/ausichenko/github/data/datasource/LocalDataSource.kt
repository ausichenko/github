package com.ausichenko.github.data.datasource

import com.ausichenko.github.data.database.dao.RepositoryDao
import com.ausichenko.github.data.models.Repository
import io.reactivex.Observable

class LocalDataSource(private val repositoryDao: RepositoryDao) : DataSource {

    fun saveRepositories(repositories: List<Repository>) {
        repositoryDao.insertAll(repositories)
    }

    fun getRepositories(searchQuery: String): Observable<List<Repository>> {
        return repositoryDao.getBySearchQuery(searchQuery)
    }
}