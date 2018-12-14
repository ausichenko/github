package com.ausichenko.github.view.search.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.network.models.GitResponse
import com.ausichenko.github.data.network.models.User
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class UsersViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private val disposable = CompositeDisposable()

    var users: SingleLiveData<GitResponse<User>, Throwable> = SingleLiveData()
    val isSuccess: LiveData<Boolean> = users.isSuccess()
    val isLoading: LiveData<Boolean> = users.isLoading()
    val isError: LiveData<Boolean> = users.isError()
    val isEmpty: LiveData<Boolean> = users.isEmpty()

    fun loadUsers(searchQueryLiveData: LiveData<String>) {
        val query = searchQueryLiveData.value.toString()
        loadUsers(query)
    }

    private fun loadUsers(searchQuery: String) {
        disposable.add(interactor.getUsers(searchQuery)
            .doOnSubscribe {
                users.load()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(users)
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}