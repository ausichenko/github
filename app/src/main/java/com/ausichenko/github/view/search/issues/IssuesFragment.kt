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
import com.ausichenko.github.databinding.FragmentSearchIssuesBinding
import com.ausichenko.github.utils.DividerItemDecoration
import com.ausichenko.github.utils.livedata.ObserverLiveData
import com.ausichenko.github.view.search.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class IssuesFragment : Fragment() {

    private val searchViewModel: SearchViewModel by sharedViewModel()
    private val issuesViewModel: IssuesViewModel by viewModel()

    private lateinit var binding: FragmentSearchIssuesBinding

    private lateinit var adapter: IssuesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_search_issues,
            container,
            false
        )
        binding.setLifecycleOwner(this)

        initRepositoriesList()

        return binding.root
    }

    private fun initRepositoriesList() {
        adapter = IssuesAdapter { issue ->
            Snackbar.make(
                binding.root,
                "Issue ".plus(issue.title).plus(" clicked"),
                Snackbar.LENGTH_LONG
            ).show()
        }
        binding.issuesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.issuesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context!!,
                R.drawable.divider
            )
        )
        binding.issuesRecyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = issuesViewModel

        prepareSingleEvents()
        prepareRepositoriesList()
    }

    private fun prepareSingleEvents() {
        searchViewModel.searchEvent.observe(this, Observer {
            issuesViewModel.loadIssues(searchViewModel.searchQuery)
        })
    }

    private fun prepareRepositoriesList() {
        issuesViewModel.issues.observe(this, Observer {
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
        issuesViewModel.loadIssues(searchViewModel.searchQuery)
    }
}