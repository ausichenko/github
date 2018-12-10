package com.ausichenko.github.data.network.models

import com.google.gson.annotations.SerializedName

data class Topic(
    var name: String,
    @SerializedName("short_description")
    var shortDescription: String?
)