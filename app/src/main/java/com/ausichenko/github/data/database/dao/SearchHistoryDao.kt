package com.ausichenko.github.data.database.dao

import androidx.room.*
import com.ausichenko.github.data.database.models.SearchHistoryDB
import io.reactivex.Single

@Dao
interface SearchHistoryDao {

    @Query("select * from search_history")
    fun getAll(): Single<List<SearchHistoryDB>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(searchHistory: SearchHistoryDB)

    @Delete
    fun delete(searchHistory: SearchHistoryDB)
}