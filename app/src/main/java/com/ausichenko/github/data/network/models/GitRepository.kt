package com.ausichenko.github.data.network.models

import com.google.gson.annotations.SerializedName

data class GitRepository(
        var id: Long,
        @SerializedName("node_id")
        var nodeId: String,
        var name: String,
        @SerializedName("full_name")
        var fullName: String,
        var owner: GitOwner,
        var private: Boolean,
        @SerializedName("html_url")
        var htmlUrl: String,
        var description: String,
        var fork: String,
        var url: String,
        @SerializedName("created_at")
        var createdAt: String,
        @SerializedName("updated_at")
        var updatedAt: String,
        @SerializedName("pushed_at")
        var pushedAt: String,
        var homepage: String,
        var size: Int,
        @SerializedName("stargazers_count")
        var stargazersCount: Int,
        @SerializedName("watchers_count")
        var watchersCount: Int,
        var language: String,
        @SerializedName("forks_count")
        var forksCount: Int,
        @SerializedName("open_issues_count")
        var openIssuesCount: Int,
        @SerializedName("master_branch")
        var masterBranch: String,
        @SerializedName("default_branch")
        var defaultBranch: String,
        @SerializedName("score")
        var score: Double
)