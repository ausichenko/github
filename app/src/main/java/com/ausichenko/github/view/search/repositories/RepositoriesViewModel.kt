package com.ausichenko.github.view.search.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ausichenko.github.data.datasource.paged.RepositoriesDataSourceFactory
import com.ausichenko.github.data.models.Repository

class RepositoriesViewModel(private val dataSourceFactory: RepositoriesDataSourceFactory) :
    ViewModel() {

    var repositories: LiveData<PagedList<Repository>>
        private set

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(60)
            .build()
        repositories = LivePagedListBuilder(dataSourceFactory, config).build()
    }
}