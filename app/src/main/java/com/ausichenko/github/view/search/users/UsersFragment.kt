package com.ausichenko.github.view.search.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ausichenko.github.R
import com.ausichenko.github.data.exceptions.FullscreenException
import com.ausichenko.github.databinding.FragmentSearchUsersBinding
import com.ausichenko.github.utils.DividerItemDecoration
import com.ausichenko.github.utils.livedata.ObserverLiveData
import com.ausichenko.github.view.search.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : Fragment() {

    private val searchViewModel: SearchViewModel by sharedViewModel()
    private val repositoriesViewModel: UsersViewModel by viewModel()

    private lateinit var binding: FragmentSearchUsersBinding

    private lateinit var adapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_search_users,
            container,
            false
        )
        binding.setLifecycleOwner(this)

        initRepositoriesList()

        return binding.root
    }

    private fun initRepositoriesList() {
        adapter = UsersAdapter { user ->
            Snackbar.make(
                binding.root,
                "UserNetwork ".plus(user.login).plus(" clicked"),
                Snackbar.LENGTH_LONG
            ).show()
        }
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.usersRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context!!,
                R.drawable.divider
            )
        )
        binding.usersRecyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = repositoriesViewModel

        prepareSingleEvents()
        prepareRepositoriesList()
    }

    private fun prepareSingleEvents() {
        searchViewModel.searchEvent.observe(this, Observer {
            repositoriesViewModel.loadUsers(searchViewModel.searchQuery)
        })
    }

    private fun prepareRepositoriesList() {
        repositoriesViewModel.users.observe(this, Observer {
            if (it.state == ObserverLiveData.DataState.SUCCESS) {
                adapter.setItems(it.data!!)
            } else if (it.state == ObserverLiveData.DataState.ERROR) {
                if (it.error is FullscreenException) {
                    val errorImage = (it.error as FullscreenException).errorImage
                    val errorMessage = (it.error as FullscreenException).errorMessage

                    binding.errorLayout.errorImage.setImageResource(errorImage)
                    binding.errorLayout.errorMessage.setText(errorMessage)
                }
            }
        })
        repositoriesViewModel.loadUsers(searchViewModel.searchQuery)
    }
}