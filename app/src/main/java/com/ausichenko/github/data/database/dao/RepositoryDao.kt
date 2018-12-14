package com.ausichenko.github.data.database.dao

import androidx.room.*
import com.ausichenko.github.data.models.Repository
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM repository")
    fun getAll(): Observable<List<Repository>>

    @Query("SELECT * FROM repository WHERE search_query LIKE (:searchQuery)")
    fun getBySearchQuery(searchQuery: String): Maybe<List<Repository>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repositories: List<Repository>)

    @Delete
    fun delete(repository: Repository)
}