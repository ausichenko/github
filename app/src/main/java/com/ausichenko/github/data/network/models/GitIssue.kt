package com.ausichenko.github.data.network.models

data class GitIssue(
    var id: Long,
    var number: Int,
    var title: String,
    var body: String
)