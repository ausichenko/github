package com.ausichenko.github.domain.interactors

import com.ausichenko.github.domain.repository.SearchRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SearchInteractor(private val repository: SearchRepository) {

    fun getRepositories(searchQuery: String): Single<List<Any>> {
        return repository.getRepositories(searchQuery)
                .subscribeOn(Schedulers.io())
    }
}