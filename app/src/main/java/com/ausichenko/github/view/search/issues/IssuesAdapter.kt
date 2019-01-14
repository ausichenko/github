package com.ausichenko.github.view.search.issues

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ausichenko.github.R
import com.ausichenko.github.data.models.Issue
import com.ausichenko.github.utils.livedata.DataState
import kotlinx.android.synthetic.main.item_issue.view.*
import java.util.*

class IssuesAdapter(private val clickListener: (Issue) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_ITEM = 1
        private const val TYPE_LOADING = 2
    }

    private val issues: MutableList<Issue> = ArrayList()
    private var currentState = DataState.SUCCESS

    fun setItems(items: List<Issue>) {
        issues.clear()
        issues.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Issue>) {
        val oldItemsCount = issues.size
        issues.addAll(items)
        notifyItemRangeInserted(oldItemsCount, issues.size)
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
        return issues.size + getExtraRow()
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
                        R.layout.item_issue,
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
        if (holder is ItemViewHolder) {
            holder.bind(issues[position])
        }
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(issue: Issue) {
            itemView.title.text = issue.title
            itemView.body.text = issue.body
            itemView.number.text = itemView.context.getString(R.string.number, issue.number)

            itemView.setOnClickListener {
                clickListener.invoke(issue)
            }
        }
    }

    inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)
}