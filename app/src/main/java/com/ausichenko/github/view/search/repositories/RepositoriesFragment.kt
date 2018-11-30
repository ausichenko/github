package com.ausichenko.github.view.search.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ausichenko.github.R
import com.ausichenko.github.databinding.FragmentSearchRepositoriesBinding
import com.ausichenko.github.utils.DividerItemDecoration
import com.ausichenko.github.utils.livedata.ObserverLiveData
import com.ausichenko.github.view.search.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoriesFragment : Fragment() {

    private val searchViewModel: SearchViewModel by sharedViewModel()
    private val repositoriesViewModel: RepositoriesViewModel by viewModel()

    private lateinit var binding: FragmentSearchRepositoriesBinding

    private lateinit var adapter: RepositoriesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_search_repositories, container, false)
        binding.setLifecycleOwner(this)

        initRepositoriesList()

        return binding.root
    }

    private fun initRepositoriesList() {
        adapter = RepositoriesAdapter { repository ->
            Snackbar.make(binding.root, "Repository ".plus(repository.fullName).plus(" clicked"), Snackbar.LENGTH_LONG).show()
        }
        binding.repositoriesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.repositoriesRecyclerView.addItemDecoration(DividerItemDecoration(context!!, R.drawable.divider))
        binding.repositoriesRecyclerView.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener { repositoriesViewModel.loadRepositories(searchViewModel.searchQuery) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = repositoriesViewModel

        prepareSingleEvents()
        prepareRepositoriesList()
    }

    private fun prepareSingleEvents() {
        searchViewModel.searchEvent.observe(this, Observer {
            repositoriesViewModel.loadRepositories(searchViewModel.searchQuery)
        })
    }

    private fun prepareRepositoriesList() {
        repositoriesViewModel.repositories.observe(this, Observer {
            if (it.state == ObserverLiveData.DataState.SUCCESS) {
                binding.swipeRefreshLayout.isRefreshing = false
                adapter.setItems(it.data!!.items)
            }
        })
        repositoriesViewModel.loadRepositories(searchViewModel.searchQuery)
    }
}