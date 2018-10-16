package com.ausichenko.github.view.main.users

import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.network.models.GitUser
import com.ausichenko.github.domain.interactors.UsersInteractor
import com.ausichenko.github.utils.livedata.ObserverLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class UsersViewModel(private val interactor: UsersInteractor) : ViewModel() {

    private val disposable = CompositeDisposable()
    private lateinit var users: ObserverLiveData<List<GitUser>, Throwable>

    fun getUsers(): ObserverLiveData<List<GitUser>, Throwable> {
        if(!::users.isInitialized) {
            users = ObserverLiveData()
            loadUsers()
        }
        return users
    }

    private fun loadUsers() {
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