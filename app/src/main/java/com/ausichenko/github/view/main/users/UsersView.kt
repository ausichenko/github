package com.ausichenko.github.view.main.users

import com.arellomobile.mvp.MvpView
import com.ausichenko.github.data.network.models.GitUser

interface UsersView : MvpView {
    fun showUsers(users: List<GitUser>)
    fun showError(error: String)
}