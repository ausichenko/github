package com.ausichenko.github.data.database.dao

import androidx.room.*
import com.ausichenko.github.data.models.Repository
import io.reactivex.Maybe

@Dao
interface RepositoryDao {

    @Query("select * from repository where search_query like (:searchQuery) order by stargazers_count desc limit 30")
    fun getBySearchQuery(searchQuery: String): Maybe<List<Repository>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repositories: List<Repository>)

    @Delete
    fun delete(repository: Repository)
}