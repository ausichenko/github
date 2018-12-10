package com.ausichenko.github.view.search.topics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ausichenko.github.R
import com.ausichenko.github.databinding.FragmentSearchTopicsBinding
import com.ausichenko.github.utils.DividerItemDecoration
import com.ausichenko.github.utils.livedata.ObserverLiveData
import com.ausichenko.github.view.search.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopicsFragment : Fragment() {

    private val searchViewModel: SearchViewModel by sharedViewModel()
    private val repositoriesViewModel: TopicsViewModel by viewModel()

    private lateinit var binding: FragmentSearchTopicsBinding

    private lateinit var adapter: TopicsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_search_topics,
            container,
            false
        )
        binding.setLifecycleOwner(this)

        initRepositoriesList()

        return binding.root
    }

    private fun initRepositoriesList() {
        adapter = TopicsAdapter { topic ->
            Snackbar.make(
                binding.root,
                "Topic ".plus(topic.name).plus(" clicked"),
                Snackbar.LENGTH_LONG
            ).show()
        }
        binding.topicRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.topicRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context!!,
                R.drawable.divider
            )
        )
        binding.topicRecyclerView.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            repositoriesViewModel.loadTopics(
                searchViewModel.searchQuery
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = repositoriesViewModel

        prepareSingleEvents()
        prepareRepositoriesList()
    }

    private fun prepareSingleEvents() {
        searchViewModel.searchEvent.observe(this, Observer {
            repositoriesViewModel.loadTopics(searchViewModel.searchQuery)
        })
    }

    private fun prepareRepositoriesList() {
        repositoriesViewModel.topics.observe(this, Observer {
            if (it.state == ObserverLiveData.DataState.SUCCESS) {
                binding.swipeRefreshLayout.isRefreshing = false
                adapter.setItems(it.data!!.items)
            }
        })
        repositoriesViewModel.loadTopics(searchViewModel.searchQuery)
    }
}