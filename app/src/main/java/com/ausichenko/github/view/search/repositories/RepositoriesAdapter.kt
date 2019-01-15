package com.ausichenko.github.view.search.repositories

import android.view.View
import com.ausichenko.github.R
import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.view.search.base.SearchListAdapter
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoriesAdapter(private val clickListener: (Repository) -> Unit) :
    SearchListAdapter<Repository>() {

    override val itemLayoutRes: Int
        get() = R.layout.item_repository

    override fun bindItemToView(item: Repository, itemView: View) {
        itemView.fullName.text = item.fullName
        if (!item.language.isNullOrEmpty()) {
            itemView.language.visibility = View.VISIBLE
            itemView.language.text = item.language
        } else {
            itemView.language.visibility = View.GONE
        }
        if (!item.description.isNullOrEmpty()) {
            itemView.description.visibility = View.VISIBLE
            itemView.description.text = item.description
        } else {
            itemView.description.visibility = View.GONE
        }
        if (item.stars > 0) {
            itemView.starsLayout.visibility = View.VISIBLE
            itemView.stars.text = item.stars.toString()
        } else {
            itemView.starsLayout.visibility = View.GONE
        }

        itemView.setOnClickListener {
            clickListener.invoke(item)
        }
    }
}