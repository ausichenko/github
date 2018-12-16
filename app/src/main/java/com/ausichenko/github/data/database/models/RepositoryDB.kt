package com.ausichenko.github.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "repositories", primaryKeys = ["id", "search_query"])
data class RepositoryDB(
    var id: Long,
    var name: String,
    @ColumnInfo(name = "full_name")
    var fullName: String,
    var description: String?,
    var stars: Int,
    var language: String?,
    @ColumnInfo(name = "search_query")
    var searchQuery: String
)