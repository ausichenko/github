package com.ausichenko.github.view.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ausichenko.github.R
import com.ausichenko.github.databinding.ActivitySearchBinding
import com.ausichenko.github.view.search.commits.CommitsFragment
import com.ausichenko.github.view.search.issues.IssuesFragment
import com.ausichenko.github.view.search.repositories.RepositoriesFragment
import com.ausichenko.github.view.search.topics.TopicsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    companion object {
        private const val REPOSITORIES_ID: Long = 1
        private const val COMMITS_ID: Long = 2
        private const val ISSUES_ID: Long = 3
        private const val TOPICS_ID: Long = 4
        private const val WIKIS_ID: Long = 5
        private const val USERS_ID: Long = 6
    }

    private val searchViewModel: SearchViewModel by viewModel()

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.viewModel = searchViewModel

        initSpinner()
        changeFragment(RepositoriesFragment())
    }

    private fun initSpinner() {
        val items: MutableList<SearchTypeAdapter.SearchItem> = ArrayList()
        items.add(SearchTypeAdapter.SearchItem(REPOSITORIES_ID, getString(R.string.repositories)))
        items.add(SearchTypeAdapter.SearchItem(COMMITS_ID, getString(R.string.commits)))
        items.add(SearchTypeAdapter.SearchItem(ISSUES_ID, getString(R.string.issues)))
        items.add(SearchTypeAdapter.SearchItem(TOPICS_ID, getString(R.string.topics)))
        items.add(SearchTypeAdapter.SearchItem(WIKIS_ID, getString(R.string.wikis)))
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
                    REPOSITORIES_ID -> changeFragment(RepositoriesFragment())
                    COMMITS_ID -> changeFragment(CommitsFragment())
                    ISSUES_ID -> changeFragment(IssuesFragment())
                    TOPICS_ID -> changeFragment(TopicsFragment())
                    WIKIS_ID -> changeFragment(Fragment())
                    USERS_ID -> changeFragment(Fragment())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
