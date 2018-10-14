package com.ausichenko.github.view.main.users

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.ausichenko.github.domain.interactors.UsersInteractor

@InjectViewState
class UsersPresenter(private val interactor: UsersInteractor): MvpPresenter<UsersView>() {
    fun loadUsers() {
        interactor.getUsers()
    }
}