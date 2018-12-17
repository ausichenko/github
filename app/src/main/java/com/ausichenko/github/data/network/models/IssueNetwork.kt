package com.ausichenko.github.data.network.models

data class IssueNetwork(
    var id: Long,
    var number: Int,
    var title: String,
    var body: String
)