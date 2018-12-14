package com.ausichenko.github.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ausichenko.github.data.database.dao.RepositoryDao
import com.ausichenko.github.data.models.Repository

@Database(entities = [Repository::class], version = 1, exportSchema = false)
abstract class GitDatabase : RoomDatabase() {

    abstract fun repositoryDao(): RepositoryDao
}