package com.ausichenko.github.view.main.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ausichenko.github.R
import com.ausichenko.github.data.network.models.GitUser
import com.ausichenko.github.utils.MvpFragment
import kotlinx.android.synthetic.main.fragment_users.view.*
import org.koin.android.ext.android.inject

class UsersFragment : MvpFragment(), UsersView {

    val presenter: UsersPresenter by inject()

    private lateinit var fragmentView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_users, container, false)

        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadUsers()
    }

    override fun showUsers(users: List<GitUser>) {
        val adapter = UsersAdapter(users)

        fragmentView.usersRecyclerView.layoutManager = LinearLayoutManager(context)
        fragmentView.usersRecyclerView.adapter = adapter
    }

    override fun showError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }
}