package com.ausichenko.github.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ausichenko.github.data.models.Repository
import io.reactivex.Observable

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM repository")
    fun getAll(): Observable<List<Repository>>

    @Query("SELECT * FROM repository WHERE search_query LIKE (:searchQuery)")
    fun getBySearchQuery(searchQuery: String): Observable<List<Repository>>

    @Insert
    fun insertAll(repositories: List<Repository>)

    @Delete
    fun delete(repository: Repository)
}