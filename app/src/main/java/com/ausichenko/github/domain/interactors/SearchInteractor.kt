package com.ausichenko.github.domain.interactors

import com.ausichenko.github.data.exceptions.StartSearchException
import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.data.models.User
import com.ausichenko.github.data.models.Commit
import com.ausichenko.github.data.models.Issue
import com.ausichenko.github.data.models.Topic
import com.ausichenko.github.domain.repository.SearchRepository
import io.reactivex.Observable

class SearchInteractor(private val repository: SearchRepository) {

    fun getRepositories(searchQuery: String): Observable<List<Repository>> {
        return if (searchQuery.isEmpty()) {
            Observable.create<List<Repository>> {
                throw StartSearchException()
            }
        } else {
            repository.getRepositories(searchQuery)
        }
    }

    fun getCommits(searchQuery: String): Observable<List<Commit>> {
        return if (searchQuery.isEmpty()) {
            Observable.create<List<Commit>> {
                throw StartSearchException()
            }
        } else {
            repository.getCommits(searchQuery)
        }
    }

    fun getIssues(searchQuery: String): Observable<List<Issue>> {
        return if (searchQuery.isEmpty()) {
            Observable.create<List<Issue>> {
                throw StartSearchException()
            }
        } else {
            repository.getIssues(searchQuery)
        }
    }

    fun getTopics(searchQuery: String): Observable<List<Topic>> {
        return if (searchQuery.isEmpty()) {
            Observable.create<List<Topic>> {
                throw StartSearchException()
            }
        } else {
            repository.getTopics(searchQuery)
        }
    }

    fun getUsers(searchQuery: String): Observable<List<User>> {
        return if (searchQuery.isEmpty()) {
            Observable.create<List<User>> {
                throw StartSearchException()
            }
        } else {
            repository.getUsers(searchQuery)
        }
    }
}