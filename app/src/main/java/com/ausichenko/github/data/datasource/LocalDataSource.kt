package com.ausichenko.github.data.datasource

import com.ausichenko.github.data.database.dao.RepositoryDao
import com.ausichenko.github.data.database.models.RepositoryDB
import io.reactivex.Single

class LocalDataSource(private val repositoryDao: RepositoryDao) : DataSource {

    fun getRepositories(searchQuery: String): Single<List<RepositoryDB>> {
        return repositoryDao.getBySearchQuery(searchQuery)
    }

    fun saveRepositories(repositories: List<RepositoryDB>) {
        repositoryDao.insertAll(repositories)
    }
}