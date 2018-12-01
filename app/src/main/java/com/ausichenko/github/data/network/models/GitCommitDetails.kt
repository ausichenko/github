package com.ausichenko.github.data.network.models

import com.google.gson.annotations.SerializedName

data class GitCommitDetails(
    var url: String,
    var author: GitCommitAuthor,
    var committer: GitCommitAuthor,
    var message: String,
    var tree: GitTree,
    @SerializedName("comment_count")
    var commentCount: Int
)