package com.ausichenko.github.data.network.models

import com.ausichenko.github.data.models.Repository

data class Commit(
    var url: String,
    var commit: CommitDetails,
    var author: Author,
    var committer: Author,
    var repository: Repository
)