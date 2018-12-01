package com.ausichenko.github.data.datasource

import com.ausichenko.github.data.network.models.GitUser
import io.reactivex.Single

class LocalDataSource : DataSource {

    private val users: LinkedHashMap<Long, GitUser> = LinkedHashMap()

    fun isEmpty(): Single<Boolean> {
        return Single.just(users.isEmpty())
    }

    fun clear() {
        users.clear()
    }

    fun saveAll(users: List<GitUser>) {
        users.forEach { user -> this.users[user.id] = user }
    }

    override fun getUsers(): Single<List<GitUser>> {
        return Single.just(users.values.toList())
    }
}