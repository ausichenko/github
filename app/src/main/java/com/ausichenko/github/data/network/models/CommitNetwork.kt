package com.ausichenko.github.data.network.models

data class CommitNetwork(
    var url: String,
    var commit: CommitDetails,
    var author: Author?,
    var committer: Author?,
    var repository: RepositoryNetwork
)