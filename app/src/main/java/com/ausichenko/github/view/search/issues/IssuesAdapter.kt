package com.ausichenko.github.view.search.issues

import android.view.View
import com.ausichenko.github.R
import com.ausichenko.github.data.models.Issue
import com.ausichenko.github.view.search.base.SearchListAdapter
import kotlinx.android.synthetic.main.item_issue.view.*

class IssuesAdapter(private val clickListener: (Issue) -> Unit) : SearchListAdapter<Issue>() {

    override val itemLayoutRes: Int
        get() = R.layout.item_issue

    override fun bindItemToView(item: Issue, itemView: View) {
        itemView.title.text = item.title
        itemView.body.text = item.body
        itemView.number.text = itemView.context.getString(R.string.number, item.number)

        itemView.setOnClickListener {
            clickListener.invoke(item)
        }
    }
}