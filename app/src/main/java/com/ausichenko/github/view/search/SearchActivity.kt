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
import com.ausichenko.github.view.search.repositories.RepositoriesFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    companion object {
        private const val repositoriesId: Long = 1
        private const val commitsId: Long = 2
        private const val issuesId: Long = 3
        private const val topicsId: Long = 4
        private const val wikisId: Long = 5
        private const val usersId: Long = 6
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
        items.add(SearchTypeAdapter.SearchItem(repositoriesId, getString(R.string.repositories)))
        items.add(SearchTypeAdapter.SearchItem(commitsId, getString(R.string.commits)))
        items.add(SearchTypeAdapter.SearchItem(issuesId, getString(R.string.issues)))
        items.add(SearchTypeAdapter.SearchItem(topicsId, getString(R.string.topics)))
        items.add(SearchTypeAdapter.SearchItem(wikisId, getString(R.string.wikis)))
        items.add(SearchTypeAdapter.SearchItem(usersId, getString(R.string.users)))

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
                    repositoriesId -> changeFragment(RepositoriesFragment())
                    commitsId -> changeFragment(CommitsFragment())
                    issuesId -> changeFragment(Fragment())
                    topicsId -> changeFragment(Fragment())
                    wikisId -> changeFragment(Fragment())
                    usersId -> changeFragment(Fragment())
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
