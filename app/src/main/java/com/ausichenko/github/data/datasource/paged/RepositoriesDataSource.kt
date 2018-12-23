package com.ausichenko.github.data.datasource.paged

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.data.network.GithubApi
import com.ausichenko.github.data.network.models.GitResponse
import com.ausichenko.github.data.network.models.RepositoryNetwork
import com.ausichenko.github.utils.ext.checkResult
import com.ausichenko.github.utils.mapper.toRepository
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RepositoriesDataSource(private val githubApi: GithubApi) :
    PageKeyedDataSource<Long, Repository>() {

    private val compositeDisposable = CompositeDisposable()
    val initialStateLiveData = MutableLiveData<DataState>()
    val paginateStateLiveData = MutableLiveData<DataState>()

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Repository>
    ) {
        initialStateLiveData.postValue(DataState.LOADING)
        val disposable =
            githubApi.getRepositories("test")
                .checkResult()
                .compose(transformRepositories())
                .toObservable()
                .compose(applySchedulers())
                .subscribe(
                    { repositories ->
                        initialStateLiveData.postValue(DataState.SUCCESS)
                        callback.onResult(repositories, null, 2)
                    },
                    { error ->
                        initialStateLiveData.postValue(DataState.ERROR)
                        // put error into initialStateLiveData
                    }
                )

        compositeDisposable.add(disposable)
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Repository>) {
        paginateStateLiveData.postValue(DataState.LOADING)
        val disposable =
            githubApi.getRepositories("test", params.key)
                .checkResult()
                .compose(transformRepositories())
                .toObservable()
                .compose(applySchedulers())
                .subscribe(
                    { repositories ->
                        paginateStateLiveData.postValue(DataState.SUCCESS)
                        callback.onResult(repositories, params.key + 1)
                    },
                    { error ->
                        paginateStateLiveData.postValue(DataState.ERROR)
                        // put error into paginateStateLiveData
                    }
                )

        compositeDisposable.add(disposable)
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Repository>) {}

    private fun transformRepositories(): SingleTransformer<GitResponse<RepositoryNetwork>, List<Repository>> {
        return SingleTransformer { observable ->
            observable.map { it.items }
                .flattenAsObservable { it }
                .map { it.toRepository() }
                .toList()
        }
    }

    private fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}