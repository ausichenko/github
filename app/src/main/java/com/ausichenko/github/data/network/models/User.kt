package com.ausichenko.github.data.network.models

import com.google.gson.annotations.SerializedName

data class User(
    var login: String,
    var id: Long,
    @SerializedName("avatar_url")
    var avatarUrl: String
)