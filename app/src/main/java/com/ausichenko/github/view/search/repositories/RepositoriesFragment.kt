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
import com.ausichenko.github.data.exceptions.FullscreenException
import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.databinding.FragmentSearchListBinding
import com.ausichenko.github.utils.DividerItemDecoration
import com.ausichenko.github.utils.bindingadapters.setVisibleOrGone
import com.ausichenko.github.utils.livedata.ObserverLiveData
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            when (it.state) {
                ObserverLiveData.DataState.SUCCESS -> handleSuccessState(it.data!!)
                ObserverLiveData.DataState.LOADING -> handleLoadingState()
                ObserverLiveData.DataState.ERROR -> handleErrorState(it.error!!)
            }
        })

        repositoriesViewModel.loadRepositories(searchViewModel.searchQuery)
    }

    private fun handleSuccessState(items: List<Repository>) {
        binding.recyclerView.setVisibleOrGone(true)
        binding.loadingLayout.root.setVisibleOrGone(false)
        binding.errorLayout.root.setVisibleOrGone(false)

        adapter.setItems(items)
    }

    private fun handleLoadingState() {
        binding.loadingLayout.root.setVisibleOrGone(true)
    }

    private fun handleErrorState(error: Throwable) {
        if (error is FullscreenException) {
            binding.recyclerView.setVisibleOrGone(false)
            binding.loadingLayout.root.setVisibleOrGone(false)
            binding.errorLayout.root.setVisibleOrGone(true)

            binding.errorLayout.errorImage.setImageResource(error.errorImage)
            binding.errorLayout.errorMessage.setText(error.errorMessage)
        }
        // other errors may show only toast/snackbar with message
    }
}