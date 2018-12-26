package com.ausichenko.github.view.search.issues

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
import com.ausichenko.github.data.exceptions.MessageException
import com.ausichenko.github.data.models.Issue
import com.ausichenko.github.databinding.FragmentSearchListBinding
import com.ausichenko.github.utils.DividerItemDecoration
import com.ausichenko.github.utils.ext.logd
import com.ausichenko.github.utils.ext.setLoadMoreListener
import com.ausichenko.github.utils.ext.setVisibleOrGone
import com.ausichenko.github.utils.livedata.DataState
import com.ausichenko.github.view.search.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class IssuesFragment : Fragment() {

    private val searchViewModel: SearchViewModel by sharedViewModel()
    private val issuesViewModel: IssuesViewModel by viewModel()

    private lateinit var binding: FragmentSearchListBinding

    private lateinit var adapter: IssuesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_search_list,
            container,
            false
        )

        initIssuesList()

        return binding.root
    }

    private fun initIssuesList() {
        adapter = IssuesAdapter { issue ->
            Snackbar.make(
                binding.root,
                "Issue ".plus(issue.title).plus(" clicked"),
                Snackbar.LENGTH_LONG
            ).show()
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context!!,
                R.drawable.divider
            )
        )
        binding.recyclerView.setLoadMoreListener {
            issuesViewModel.loadMore()
        }
        binding.recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prepareSingleEvents()
        prepareIssuesList()
    }

    private fun prepareSingleEvents() {
        searchViewModel.getSearchEvent().observe(this, Observer {
            issuesViewModel.loadIssues(searchViewModel.getSearchQuery())
        })
    }

    private fun prepareIssuesList() {
        issuesViewModel.initialState.observe(this, Observer {
            when (it.state) {
                DataState.INIT -> logd("init")
                DataState.SUCCESS -> handleSuccessState(it.data!!)
                DataState.LOADING -> handleLoadingState()
                DataState.ERROR -> handleErrorState(it.error!!)
            }
        })
        issuesViewModel.pagedState.observe(this, Observer { it ->
            adapter.setState(it.state)
            if (it.state == DataState.SUCCESS) {
                adapter.addItems(it.data!!)
            }
        })

        issuesViewModel.loadIssues(searchViewModel.getSearchQuery())
    }

    private fun handleSuccessState(items: List<Issue>) {
        binding.recyclerView.setVisibleOrGone(true)
        binding.loadingLayout.root.setVisibleOrGone(false)
        binding.errorLayout.root.setVisibleOrGone(false)

        adapter.setItems(items)
    }

    private fun handleLoadingState() {
        binding.loadingLayout.root.setVisibleOrGone(true)
    }

    private fun handleErrorState(error: Throwable) {
        when (error) {
            is MessageException -> {
                binding.loadingLayout.root.setVisibleOrGone(false)

                Snackbar.make(binding.root, error.errorMessage, Snackbar.LENGTH_LONG).show()
            }
            is FullscreenException -> {
                binding.recyclerView.setVisibleOrGone(false)
                binding.loadingLayout.root.setVisibleOrGone(false)
                binding.errorLayout.root.setVisibleOrGone(true)

                binding.errorLayout.errorImage.setImageResource(error.errorImage)
                binding.errorLayout.errorMessage.setText(error.errorMessage)
            }
        }
    }
}