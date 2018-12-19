package com.ausichenko.github.view.search

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ausichenko.github.R
import com.ausichenko.github.databinding.ActivitySearchBinding
import com.ausichenko.github.utils.ext.onTextChangedListener
import com.ausichenko.github.utils.ext.setOnSearchListener
import com.ausichenko.github.utils.ext.setVisibleOrGone
import com.ausichenko.github.view.search.commits.CommitsFragment
import com.ausichenko.github.view.search.issues.IssuesFragment
import com.ausichenko.github.view.search.repositories.RepositoriesFragment
import com.ausichenko.github.view.search.topics.TopicsFragment
import com.ausichenko.github.view.search.users.UsersFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    companion object {
        private const val REPOSITORIES_ID: Long = 1
        private const val COMMITS_ID: Long = 2
        private const val ISSUES_ID: Long = 3
        private const val TOPICS_ID: Long = 4
        private const val USERS_ID: Long = 5
    }

    private val searchViewModel: SearchViewModel by viewModel()

    private lateinit var binding: ActivitySearchBinding

    private val repositoriesFragment = RepositoriesFragment()
    private val commitsFragment = CommitsFragment()
    private val issuesFragment = IssuesFragment()
    private val topicsFragment = TopicsFragment()
    private val usersFragment = UsersFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        initSearch()
        initSearchAutocomplete()
        initSpinner()
        initNetworkBanner()

        changeFragment(repositoriesFragment)
    }

    private fun initSearch() {
        binding.searchBar.onTextChangedListener { text ->
            searchViewModel.searchQuery.postValue(text.trim())
        }

        binding.searchBar.setOnSearchListener {
            searchViewModel.onSearch()
        }
    }

    private fun initSearchAutocomplete() {
        val adapter =
            ArrayAdapter(this, R.layout.item_search, R.id.title, ArrayList<String>())
        binding.searchBar.setAdapter(adapter)
        binding.searchBar.dropDownVerticalOffset =
                resources.getDimension(R.dimen.search_card_margin).toInt()

        searchViewModel.searchHistory.observe(this, Observer { items ->
            adapter.clear()
            adapter.addAll(items)
            adapter.notifyDataSetChanged()
        })
        searchViewModel.loadSearchHistory()
    }

    private fun initSpinner() {
        val items: MutableList<SearchTypeAdapter.SearchItem> = ArrayList()
        items.add(SearchTypeAdapter.SearchItem(REPOSITORIES_ID, getString(R.string.repositories)))
        items.add(SearchTypeAdapter.SearchItem(COMMITS_ID, getString(R.string.commits)))
        items.add(SearchTypeAdapter.SearchItem(ISSUES_ID, getString(R.string.issues)))
        items.add(SearchTypeAdapter.SearchItem(TOPICS_ID, getString(R.string.topics)))
        items.add(SearchTypeAdapter.SearchItem(USERS_ID, getString(R.string.users)))

        val adapter = SearchTypeAdapter(applicationContext, items)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (id) {
                    REPOSITORIES_ID -> changeFragment(repositoriesFragment)
                    COMMITS_ID -> changeFragment(commitsFragment)
                    ISSUES_ID -> changeFragment(issuesFragment)
                    TOPICS_ID -> changeFragment(topicsFragment)
                    USERS_ID -> changeFragment(usersFragment)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun initNetworkBanner() {
        binding.turnOnWifi.setOnClickListener {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }
        searchViewModel.initNetworkObserver(applicationContext)
        searchViewModel.isOnline.observe(this, Observer { isOnline ->
            binding.networkBanner.setVisibleOrGone(!isOnline)
            if (isOnline) {
                searchViewModel.onSearch()
            }
        })
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
