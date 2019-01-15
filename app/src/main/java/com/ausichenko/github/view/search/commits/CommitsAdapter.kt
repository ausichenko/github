package com.ausichenko.github.view.search.commits

import android.view.View
import com.ausichenko.github.R
import com.ausichenko.github.data.models.Commit
import com.ausichenko.github.view.search.base.SearchListAdapter
import kotlinx.android.synthetic.main.item_commit.view.*

class CommitsAdapter(private val clickListener: (Commit) -> Unit) : SearchListAdapter<Commit>() {

    override val itemLayoutRes: Int
        get() = R.layout.item_commit

    override fun bindItemToView(item: Commit, itemView: View) {
        itemView.name.text = item.commitMessage

        if (item.authorLogin != null) {
            if (item.committerLogin != null && !item.authorLogin.equals(item.committerLogin)) {
                itemView.info.text = itemView.context.getString(
                    R.string.commit_info_authored_committed,
                    item.authorLogin,
                    item.committerLogin,
                    item.repositoryName
                )
            } else {
                itemView.info.text = itemView.context.getString(
                    R.string.commit_info_committed,
                    item.authorLogin,
                    item.repositoryName
                )
            }
        } else {
            itemView.info.text = itemView.context.getString(
                R.string.commit_info_committed,
                itemView.context.getString(R.string.git_anon),
                item.repositoryName
            )
        }

        itemView.setOnClickListener {
            clickListener.invoke(item)
        }
    }
}