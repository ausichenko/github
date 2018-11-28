package com.ausichenko.github.data.network.models

import com.google.gson.annotations.SerializedName

data class GitResponse<T>(
        @SerializedName("total_count")
        var totalCount: Int,
        @SerializedName("incomplete_results")
        var incompleteResults: Boolean,
        var items: List<T>
)