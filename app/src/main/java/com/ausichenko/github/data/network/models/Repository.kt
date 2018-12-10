package com.ausichenko.github.data.network.models

import com.google.gson.annotations.SerializedName

data class Repository(
    var id: Long,
    var name: String,
    @SerializedName("full_name")
    var fullName: String,
    var description: String?,
    @SerializedName("stargazers_count")
    var stargazersCount: Int,
    var language: String?
)