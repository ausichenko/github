package com.ausichenko.github.data.network.models

data class Issue(
    var id: Long,
    var number: Int,
    var title: String,
    var body: String
)