package com.ausichenko.github.data.datasource

import com.ausichenko.github.data.network.models.User
import io.reactivex.Single

class LocalDataSource : DataSource {

    private val users: LinkedHashMap<Long, User> = LinkedHashMap()

    fun isEmpty(): Single<Boolean> {
        return Single.just(users.isEmpty())
    }

    fun clear() {
        users.clear()
    }

    fun saveAll(users: List<User>) {
        users.forEach { user -> this.users[user.id] = user }
    }

    override fun getUsers(): Single<List<User>> {
        return Single.just(users.values.toList())
    }
}