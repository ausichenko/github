package com.ausichenko.github.di

import android.content.Context
import androidx.room.Room
import com.ausichenko.github.data.database.GitDatabase
import com.ausichenko.github.data.database.dao.SearchHistoryDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val databaseModule = module {
    single { makeDatabase(androidContext()) }
    single { makeRepositoriesDao(get()) }
}

fun makeDatabase(context: Context): GitDatabase {
    return Room
        .databaseBuilder(context, GitDatabase::class.java, "github_database")
        .build()
}

fun makeRepositoriesDao(gitDatabase: GitDatabase): SearchHistoryDao {
    return gitDatabase.searchHistoryDao()
}