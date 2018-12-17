package com.ausichenko.github.domain.repository

import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.data.models.User
import com.ausichenko.github.data.models.Commit
import com.ausichenko.github.data.models.Issue
import com.ausichenko.github.data.models.Topic
import io.reactivex.Observable

interface SearchRepository {
    fun getRepositories(searchQuery: String): Observable<List<Repository>>
    fun getCommits(searchQuery: String): Observable<List<Commit>>
    fun getIssues(searchQuery: String): Observable<List<Issue>>
    fun getTopics(searchQuery: String): Observable<List<Topic>>
    fun getUsers(searchQuery: String): Observable<List<User>>
}