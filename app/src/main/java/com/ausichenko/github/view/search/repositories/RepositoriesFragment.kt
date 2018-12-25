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
import com.ausichenko.github.data.datasource.paged.DataState
import com.ausichenko.github.data.exceptions.FullscreenException
import com.ausichenko.github.data.exceptions.MessageException
import com.ausichenko.github.databinding.FragmentSearchListBinding
import com.ausichenko.github.utils.DividerItemDecoration
import com.ausichenko.github.utils.ext.setVisibleOrGone
import com.ausichenko.github.view.search.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoriesFragment : Fragment() {

    private val searchViewModel: SearchViewModel by sharedViewModel()
    private val repositoriesViewModel: RepositoriesViewModel by viewModel()

    private lateinit var binding: FragmentSearchListBinding

    private lateinit var adapter: RepositoriesAdapter

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

        initRepositoriesList()

        return binding.root
    }

    private fun initRepositoriesList() {
        adapter = RepositoriesAdapter { repository ->
            Snackbar.make(
                binding.root,
                "Repository ".plus(repository.fullName).plus(" clicked"),
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
        binding.recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prepareSingleEvents()
        prepareStates()
        repositoriesViewModel.repositories.observe(this, Observer { pagedList ->
            adapter.submitList(pagedList)
        })
    }

    private fun prepareSingleEvents() {
        searchViewModel.getSearchEvent().observe(this, Observer {
            repositoriesViewModel.loadData(searchViewModel.getSearchQuery())
            // load new data with search query
            //repositoriesViewModel.loadRepositories(searchViewModel.getSearchQuery())
        })
    }

    private fun prepareStates() {
        repositoriesViewModel.getInitialStateLiveData().observe(this, Observer { state ->
            when (state!!) {
                DataState.LOADING -> handleLoadingState()
                DataState.SUCCESS -> handleSuccessState()
                DataState.ERROR -> handleErrorState()
            }
        })
        repositoriesViewModel.getPaginateStateLiveData().observe(this, Observer { state ->
            adapter.setPaginateState(state)
        })
    }

    private fun handleSuccessState() {
        binding.recyclerView.setVisibleOrGone(true)
        binding.loadingLayout.root.setVisibleOrGone(false)
        binding.errorLayout.root.setVisibleOrGone(false)
    }

    private fun handleLoadingState() {
        binding.loadingLayout.root.setVisibleOrGone(true)
    }

    private fun handleErrorState(error: Throwable? = null) {
        binding.recyclerView.setVisibleOrGone(false)
        binding.loadingLayout.root.setVisibleOrGone(false)
        binding.errorLayout.root.setVisibleOrGone(true)
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