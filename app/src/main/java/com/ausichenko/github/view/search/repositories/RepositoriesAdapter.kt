package com.ausichenko.github.view.search.repositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ausichenko.github.R
import com.ausichenko.github.data.models.Repository
import com.ausichenko.github.utils.livedata.DataState
import kotlinx.android.synthetic.main.item_repository.view.*
import java.util.*

class RepositoriesAdapter(private val clickListener: (Repository) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_ITEM = 1
        private const val TYPE_LOADING = 2
    }

    private val repositories: MutableList<Repository> = ArrayList()
    private var currentState = DataState.SUCCESS

    fun setItems(items: List<Repository>) {
        repositories.clear()
        repositories.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Repository>) {
        val oldItemsCount = repositories.size
        repositories.addAll(items)
        notifyItemRangeInserted(oldItemsCount, repositories.size)
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
        return repositories.size + getExtraRow()
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
                        R.layout.item_repository,
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
            holder.bind(repositories[position])
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
}