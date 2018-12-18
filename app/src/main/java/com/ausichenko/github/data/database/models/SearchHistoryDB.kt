package com.ausichenko.github.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryDB(
    @PrimaryKey
    @ColumnInfo(name = "search_query")
    var searchQuery: String
)