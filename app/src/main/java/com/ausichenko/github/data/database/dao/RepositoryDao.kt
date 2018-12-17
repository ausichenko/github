package com.ausichenko.github.data.database.dao

import androidx.room.*
import com.ausichenko.github.data.database.models.RepositoryDB
import io.reactivex.Single

@Dao
interface RepositoryDao {

    @Query("select * from repositories where search_query like (:searchQuery) limit 30")
    fun getBySearchQuery(searchQuery: String): Single<List<RepositoryDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repositories: List<RepositoryDB>)

    @Delete
    fun delete(repository: RepositoryDB)
}