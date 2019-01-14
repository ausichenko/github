package com.ausichenko.github.view.search.commits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ausichenko.github.R
import com.ausichenko.github.data.models.Commit
import com.ausichenko.github.utils.livedata.DataState
import kotlinx.android.synthetic.main.item_commit.view.*
import java.util.*

class CommitsAdapter(private val clickListener: (Commit) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_ITEM = 1
        private const val TYPE_LOADING = 2
    }

    private val commits: MutableList<Commit> = ArrayList()
    private var currentState = DataState.SUCCESS

    fun setItems(items: List<Commit>) {
        commits.clear()
        commits.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Commit>) {
        val oldItemsCount = commits.size
        commits.addAll(items)
        notifyItemRangeInserted(oldItemsCount, commits.size)
    }

    fun setState(newState: DataState) {
        val previousState = currentState
        val hadExtraRow = hasExtraRow()
        currentState = newState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (hasExtraRow && previousState != newState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    override fun getItemCount(): Int {
        return commits.size + getExtraRow()
    }

    private fun getExtraRow(): Int {
        return if (hasExtraRow())
            1
        else
            0
    }

    private fun hasExtraRow(): Boolean {
        return currentState == DataState.LOADING
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            TYPE_LOADING
        } else {
            TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_ITEM ->
                return ItemViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_commit,
                        parent,
                        false
                    )
                )
            else ->
                return LoadingViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_loading,
                        parent,
                        false
                    )
                )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CommitsAdapter.ItemViewHolder) {
            holder.bind(commits[position])
        }
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(commit: Commit) {
            itemView.name.text = commit.commitMessage

            if (commit.authorLogin != null) {
                if (commit.committerLogin != null && !commit.authorLogin.equals(commit.committerLogin)) {
                    itemView.info.text = itemView.context.getString(
                        R.string.commit_info_authored_committed,
                        commit.authorLogin,
                        commit.committerLogin,
                        commit.repositoryName
                    )
                } else {
                    itemView.info.text = itemView.context.getString(
                        R.string.commit_info_committed,
                        commit.authorLogin,
                        commit.repositoryName
                    )
                }
            } else {
                itemView.info.text = itemView.context.getString(
                    R.string.commit_info_committed,
                    itemView.context.getString(R.string.git_anon),
                    commit.repositoryName
                )
            }

            itemView.setOnClickListener {
                clickListener.invoke(commit)
            }
        }
    }

    inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)
}