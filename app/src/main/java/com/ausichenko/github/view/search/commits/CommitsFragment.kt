package com.ausichenko.github.view.search.commits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ausichenko.github.R
import com.ausichenko.github.databinding.FragmentSearchCommitsBinding
import com.ausichenko.github.utils.DividerItemDecoration
import com.ausichenko.github.utils.livedata.ObserverLiveData
import com.ausichenko.github.view.search.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommitsFragment : Fragment() {

    private val searchViewModel: SearchViewModel by sharedViewModel()
    private val commitsViewModel: CommitsViewModel by viewModel()

    private lateinit var binding: FragmentSearchCommitsBinding

    private lateinit var adapter: CommitsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_search_commits, container, false)
        binding.setLifecycleOwner(this)

        initRepositoriesList()

        return binding.root
    }

    private fun initRepositoriesList() {
        adapter = CommitsAdapter { commit ->
            Snackbar.make(binding.root, "Commit ".plus(commit.commit.message).plus(" clicked"), Snackbar.LENGTH_LONG).show()
        }
        binding.commitsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.commitsRecyclerView.addItemDecoration(DividerItemDecoration(context!!, R.drawable.divider))
        binding.commitsRecyclerView.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener { commitsViewModel.loadCommits(searchViewModel.searchQuery) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = commitsViewModel

        prepareSingleEvents()
        prepareRepositoriesList()
    }

    private fun prepareSingleEvents() {
        searchViewModel.searchEvent.observe(this, Observer {
            commitsViewModel.loadCommits(searchViewModel.searchQuery)
        })
    }

    private fun prepareRepositoriesList() {
        commitsViewModel.commits.observe(this, Observer {
            if (it.state == ObserverLiveData.DataState.SUCCESS) {
                binding.swipeRefreshLayout.isRefreshing = false
                adapter.setItems(it.data!!.items)
            }
        })
        commitsViewModel.loadCommits(searchViewModel.searchQuery)
    }
}