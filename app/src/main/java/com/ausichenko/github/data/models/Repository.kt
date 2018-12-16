package com.ausichenko.github.data.models

data class Repository(
    var id: Long,
    var name: String,
    var fullName: String,
    var description: String?,
    var stars: Int,
    var language: String?
)