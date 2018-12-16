package com.ausichenko.github.utils.mapper

import com.ausichenko.github.data.database.models.RepositoryDB
import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.data.network.models.RepositoryNetwork

fun RepositoryNetwork.toRepositoryDB(
    searchQuery: String
): RepositoryDB {
    return RepositoryDB(
        this.id,
        this.name,
        this.fullName,
        this.description,
        this.stargazersCount,
        this.language,
        searchQuery
    )
}

fun RepositoryDB.toRepositoryNetwork(): RepositoryNetwork {
    return RepositoryNetwork(
        this.id,
        this.name,
        this.fullName,
        this.description,
        this.stars,
        this.language
    )
}

fun RepositoryDB.toRepository(): Repository {
    return Repository(
        this.id,
        this.name,
        this.fullName,
        this.description,
        this.stars,
        this.language
    )
}

fun RepositoryNetwork.toRepository(): Repository {
    return Repository(
        this.id,
        this.name,
        this.fullName,
        this.description,
        this.stargazersCount,
        this.language
    )
}