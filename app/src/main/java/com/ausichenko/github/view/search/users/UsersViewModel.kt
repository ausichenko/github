package com.ausichenko.github.view.search.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.models.User
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.ObserverLiveData
import io.reactivex.android.schedulers.AndroidSchedulers

class UsersViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private lateinit var users: ObserverLiveData<List<User>>

    fun getUsers(searchQueryLiveData: LiveData<String>): ObserverLiveData<List<User>> {
        if (!::users.isInitialized) {
            users = ObserverLiveData()
            loadUsers(searchQueryLiveData)
        }
        return users
    }

    fun loadUsers(searchQueryLiveData: LiveData<String>) {
        val query = searchQueryLiveData.value.toString()
        loadUsers(query)
    }

    private fun loadUsers(searchQuery: String) {
        interactor.getUsers(searchQuery)
            .doOnSubscribe {
                users.load()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(users)
    }
}