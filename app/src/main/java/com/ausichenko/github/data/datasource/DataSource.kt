package com.ausichenko.github.data.datasource

import com.ausichenko.github.data.network.models.GitUser
import io.reactivex.Single

interface DataSource {
    fun getUsers(): Single<List<GitUser>>
}

// todo: think about split data layer to: data, cache and remote
// todo: where this DataSource class is base interface for RemoteDateSource and CacheDataSource

/* current structure:
        data - database
             - preference
             - network
             - repository

    next stage(0):
        data - datasource
             - repository

        cache - datasource impl

        remote - datasource impl

    next: stage(1):
        add mapper to data layer
*/