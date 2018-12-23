package com.ausichenko.github.view.search.repositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ausichenko.github.R
import com.ausichenko.github.data.datasource.paged.DataState
import com.ausichenko.github.data.models.Repository
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoriesAdapter(private val clickListener: (Repository) -> Unit) :
    PagedListAdapter<Repository, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        private const val TYPE_ITEM = 1
        private const val TYPE_LOADING = 2
        private const val TYPE_ERROR = 3

        private val diffCallback: DiffUtil.ItemCallback<Repository> =
            object : DiffUtil.ItemCallback<Repository>() {
                override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                    return oldItem == newItem
                }
            }
    }

    private var currentState: DataState = DataState.SUCCESS

    fun setPaginateState(newState: DataState) {
        val previousState = currentState
        val hadExtraRow = hasExtraRow()
        currentState = newState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + getExtraRow()
    }

    private fun getExtraRow(): Int {
        return if (hasExtraRow())
            1
        else
            0
    }

    private fun hasExtraRow(): Boolean {
        return currentState != DataState.SUCCESS
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            if (currentState == DataState.LOADING) {
                TYPE_LOADING
            } else {
                TYPE_ERROR
            }
        } else {
            TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_ITEM ->
                return ItemViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_repository,
                        parent,
                        false
                    )
                )
            TYPE_LOADING ->
                return LoadingViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_loading,
                        parent,
                        false
                    )
                )
            else ->
                return ErrorViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_error,
                        parent,
                        false
                    )
                )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                getItem(position)?.let { repository -> holder.bind(repository) }
            }
            is LoadingViewHolder -> {
                // bind loading if it needed
            }
            is ErrorViewHolder -> {
                // bind loading if it needed
            }
        }
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(repository: Repository) {
            itemView.fullName.text = repository.fullName
            if (!repository.language.isNullOrEmpty()) {
                itemView.language.visibility = View.VISIBLE
                itemView.language.text = repository.language
            } else {
                itemView.language.visibility = View.GONE
            }
            if (!repository.description.isNullOrEmpty()) {
                itemView.description.visibility = View.VISIBLE
                itemView.description.text = repository.description
            } else {
                itemView.description.visibility = View.GONE
            }
            if (repository.stars > 0) {
                itemView.starsLayout.visibility = View.VISIBLE
                itemView.stars.text = repository.stars.toString()
            } else {
                itemView.starsLayout.visibility = View.GONE
            }

            itemView.setOnClickListener {
                clickListener.invoke(repository)
            }
        }
    }

    inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class ErrorViewHolder(view: View) : RecyclerView.ViewHolder(view)
}