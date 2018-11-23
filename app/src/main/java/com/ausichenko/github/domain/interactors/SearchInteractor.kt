package com.ausichenko.github.domain.interactors

import com.ausichenko.github.domain.repository.SearchRepository
import io.reactivex.Single

class SearchInteractor(private val repository: SearchRepository) {
    fun getRepositories(): Single<List<Any>> {
        return Single.just(ArrayList())
    }
}