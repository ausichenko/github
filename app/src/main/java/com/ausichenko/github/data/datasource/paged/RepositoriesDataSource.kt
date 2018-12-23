package com.ausichenko.github.data.datasource.paged

import androidx.paging.PageKeyedDataSource
import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.data.network.GithubApi
import androidx.lifecycle.MutableLiveData
import com.ausichenko.github.utils.ext.checkResult
import com.ausichenko.github.utils.ext.logd
import com.ausichenko.github.utils.mapper.toRepository
import io.reactivex.disposables.CompositeDisposable

class RepositoriesDataSource(private val githubApi: GithubApi) :
    PageKeyedDataSource<Long, Repository>() {

    private val compositeDisposable = CompositeDisposable()

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Repository>
    ) {
        compositeDisposable.add(
            githubApi.getRepositories("test")
                .checkResult()
                .map { it.items }
                .flattenAsObservable { it }
                .map { it.toRepository() }
                .toList()
                .toObservable()
                .subscribe(
                    { repositories ->
                        //states
                        callback.onResult(repositories, null, 2)
                        logd("loaded")
                    },
                    { error ->

                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Repository>) {
        compositeDisposable.add(
            githubApi.getRepositories("test", params.key)
                .checkResult()
                .map { it.items }
                .flattenAsObservable { it }
                .map { it.toRepository() }
                .toList()
                .toObservable()
                .subscribe(
                    { repositories ->
                        //states
                        callback.onResult(repositories, params.key + 1)
                        logd("loaded")
                    },
                    { error ->

                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Repository>) {}

}