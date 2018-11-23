package com.ausichenko.github.view.main.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.network.models.GitUser
import com.ausichenko.github.domain.interactors.UsersInteractor
import com.ausichenko.github.utils.livedata.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class UsersViewModel(private val interactor: UsersInteractor) : ViewModel() {

    private val disposable = CompositeDisposable()

    var users: ObserverLiveData<List<GitUser>, Throwable> = ObserverLiveData()
    val isSuccess: LiveData<Boolean> = users.isSuccess()
    val isLoading: LiveData<Boolean> = users.isLoading()
    val isError: LiveData<Boolean> = users.isError()
    val isEmpty: LiveData<Boolean> = users.isEmpty()

    fun loadUsers() {
        disposable.add(interactor.getUsers()
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