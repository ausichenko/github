package com.ausichenko.github.data.network.models

import com.google.gson.annotations.SerializedName

data class GitCommit(
    var url: String,
    var sha: String,
    @SerializedName("html_url")
    var htmlUrl: String,
    @SerializedName("comments_url")
    var commentsUrl: String,
    var commit: GitCommitDetails,
    var author: GitAuthor,
    var commiter: GitAuthor,
    var parents: List<GitParent>,
    var repository: GitRepository,
    var score: Double
)