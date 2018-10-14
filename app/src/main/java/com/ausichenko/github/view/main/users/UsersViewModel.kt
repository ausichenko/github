package com.ausichenko.github.view.main.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.network.models.GitUser
import com.ausichenko.github.domain.interactors.UsersInteractor
import com.ausichenko.github.utils.ActionLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class UsersViewModel(private val interactor: UsersInteractor) : ViewModel() {

    private val disposable = CompositeDisposable()
    private lateinit var users: MutableLiveData<List<GitUser>>
    val errorAction = ActionLiveData<ErrorMessage>()

    fun getUsers(): LiveData<List<GitUser>> {
        if(!::users.isInitialized) {
            users = MutableLiveData()
            loadUsers()
        }
        return users
    }

    private fun loadUsers() {
        disposable.add(interactor.getUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadSuccess, this::onLoadError)
        )
    }

    private fun onLoadSuccess(userList: List<GitUser>) {
        users.value = userList
    }

    private fun onLoadError(throwable: Throwable) {
        errorAction.sendAction(ErrorMessage(throwable.message.toString()))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    data class ErrorMessage(val error: String)
}