package com.ausichenko.github.utils.mapper

import com.ausichenko.github.data.models.*
import com.ausichenko.github.data.network.models.*

fun RepositoryNetwork.toRepository(): Repository {
    return Repository(
        this.id,
        this.name,
        this.fullName,
        this.description,
        this.stargazersCount,
        this.language
    )
}

fun CommitNetwork.toCommit(): Commit {
    return Commit(
        this.commit.message,
        this.repository.fullName,
        this.author?.login,
        this.committer?.login
    )
}

fun IssueNetwork.toIssue(): Issue {
    return Issue(
        this.id,
        this.number,
        this.title,
        this.body
    )
}

fun TopicNetwork.toTopic(): Topic {
    return Topic(
        this.name,
        this.shortDescription
    )
}

fun UserNetwork.toUser(): User {
    return User(
        this.login,
        this.id,
        this.avatarUrl
    )
}