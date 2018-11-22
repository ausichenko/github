package com.ausichenko.github.view.main.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ausichenko.github.R
import com.ausichenko.github.databinding.FragmentUsersBinding
import com.ausichenko.github.utils.livedata.ObserverLiveData
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : Fragment() {

    private val usersViewModel: UsersViewModel by viewModel()

    private lateinit var binding: FragmentUsersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_users, container, false)
        binding.setLifecycleOwner(this)

        binding.swipeRefreshLayout.setOnRefreshListener { usersViewModel.loadUsers() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = usersViewModel
        usersViewModel.users.observe(this, Observer {
            if (it.state == ObserverLiveData.DataState.SUCCESS) {
                binding.swipeRefreshLayout.isRefreshing = false
                val adapter = UsersAdapter(it.data!!)

                binding.usersRecyclerView.layoutManager = LinearLayoutManager(context)
                binding.usersRecyclerView.adapter = adapter
            }
        })
        usersViewModel.loadUsers()
    }
}