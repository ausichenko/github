package com.ausichenko.github.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ausichenko.github.data.database.dao.SearchHistoryDao
import com.ausichenko.github.data.database.models.SearchHistoryDB

@Database(entities = [SearchHistoryDB::class], version = 1, exportSchema = false)
abstract class GitDatabase : RoomDatabase() {

    abstract fun searchHistoryDao(): SearchHistoryDao
}