package com.ausichenko.github.view.search.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ausichenko.github.data.network.models.User
import com.ausichenko.github.domain.interactors.SearchInteractor
import com.ausichenko.github.utils.livedata.ObserverLiveData
import com.ausichenko.github.utils.livedata.isError
import com.ausichenko.github.utils.livedata.isLoading
import io.reactivex.android.schedulers.AndroidSchedulers

class UsersViewModel(private val interactor: SearchInteractor) : ViewModel() {

    var users: ObserverLiveData<List<User>> = ObserverLiveData()
    val isLoading: LiveData<Boolean> = users.isLoading()
    val isError: LiveData<Boolean> = users.isError()

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