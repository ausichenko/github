package com.ausichenko.github.data.datasource.paged

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ausichenko.github.data.models.Repository

class RepositoriesDataSourceFactory(private val dataSource: RepositoriesDataSource) :
    DataSource.Factory<Long, Repository>() {

    private val dataSourceLiveData = MutableLiveData<RepositoriesDataSource>()

    override fun create(): DataSource<Long, Repository> {
        dataSourceLiveData.postValue(dataSource)
        return dataSource
    }

    fun getInitialStateLiveData(): MutableLiveData<DataState> {
        return dataSource.initialStateLiveData
    }

    fun getPaginateStateLiveData(): MutableLiveData<DataState> {
        return dataSource.paginateStateLiveData
    }

    fun loadData(searchQuery: String) {
        dataSource.searchQuery = searchQuery
    }
}