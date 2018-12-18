package com.ausichenko.github.data.datasource

import com.ausichenko.github.data.database.dao.SearchHistoryDao
import com.ausichenko.github.data.database.models.SearchHistoryDB
import io.reactivex.Single

class LocalDataSource(private val searchHistoryDao: SearchHistoryDao) : DataSource {

    fun getSearchHistory(): Single<List<SearchHistoryDB>> {
        return searchHistoryDao.getAll()
    }

    fun saveSearchHistory(searchHistory: SearchHistoryDB) {
        return searchHistoryDao.insert(searchHistory)
    }
}