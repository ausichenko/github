package com.ausichenko.github.view.main.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ausichenko.github.R
import com.ausichenko.github.utils.MvpFragment
import kotlinx.android.synthetic.main.fragment_users.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : MvpFragment() {

    val usersViewModel: UsersViewModel by viewModel()

    private lateinit var fragmentView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_users, container, false)

        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersViewModel.getUsers().observe(this, Observer {
            val adapter = UsersAdapter(it)

            fragmentView.usersRecyclerView.layoutManager = LinearLayoutManager(context)
            fragmentView.usersRecyclerView.adapter = adapter
        })
        usersViewModel.errorAction.observe(this, Observer {
            Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
        })
    }
}