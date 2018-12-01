package com.ausichenko.github.data.network.models

import com.google.gson.annotations.SerializedName

data class GitParent(
    var url: String,
    @SerializedName("html_url")
    var htmlUrl: String,
    var sha: String
)