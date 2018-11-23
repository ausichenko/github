package com.ausichenko.github.view.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ausichenko.github.R
import com.ausichenko.github.databinding.FragmentSearchRepositoriesBinding
import com.ausichenko.github.view.main.users.UsersFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModel()

    private lateinit var binding: FragmentSearchRepositoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_search)

        changeFragment(UsersFragment())
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
    }
}
