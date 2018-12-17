package com.ausichenko.github.data.models

data class Commit(
    var commitMessage: String,
    var repositoryName: String,
    var authorLogin: String?,
    var committerLogin: String?

)