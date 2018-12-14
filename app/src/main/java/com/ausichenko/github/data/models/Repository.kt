package com.ausichenko.github.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Repository(
    @PrimaryKey
    var id: Long,
    var name: String,
    @ColumnInfo(name = "full_name")
    @SerializedName("full_name")
    var fullName: String,
    var description: String?,
    @ColumnInfo(name = "stargazers_count")
    @SerializedName("stargazers_count")
    var stargazersCount: Int,
    var language: String?,
    @ColumnInfo(name = "search_query")
    var searchQuery: String
)