package com.ausichenko.github.view.main.users

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ausichenko.github.data.network.models.GitUser
import com.ausichenko.github.domain.interactors.UsersInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class UsersPresenter(private val interactor: UsersInteractor): MvpPresenter<UsersView>() {

    private val compositeDisposable = CompositeDisposable()

    fun loadUsers() {
        compositeDisposable.add(interactor.getUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadSuccess, this::onLoadError))
    }

    private fun onLoadSuccess(users: List<GitUser>) {
        viewState.showUsers(users)
    }

    private fun onLoadError(throwable: Throwable) {
        viewState.showError(throwable.message.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}