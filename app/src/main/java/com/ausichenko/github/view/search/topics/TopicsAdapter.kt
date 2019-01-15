package com.ausichenko.github.view.search.topics

import android.view.View
import com.ausichenko.github.R
import com.ausichenko.github.data.models.Topic
import com.ausichenko.github.view.search.base.SearchListAdapter
import kotlinx.android.synthetic.main.item_topic.view.*

class TopicsAdapter(private val clickListener: (Topic) -> Unit) : SearchListAdapter<Topic>() {

    override val itemLayoutRes: Int
        get() = R.layout.item_topic

    override fun bindItemToView(item: Topic, itemView: View) {
        itemView.name.text = item.name

        if (!item.shortDescription.isNullOrEmpty()) {
            itemView.description.text = item.shortDescription
            itemView.description.visibility = View.VISIBLE
        } else {
            itemView.description.visibility = View.GONE
        }

        itemView.setOnClickListener {
            clickListener.invoke(item)
        }
    }
}