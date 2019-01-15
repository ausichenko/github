package com.ausichenko.github.view.search.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ausichenko.github.R
import com.ausichenko.github.utils.livedata.DataState

abstract class SearchListAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected companion object {
        const val TYPE_ITEM = 1
        const val TYPE_LOADING = 2
    }

    abstract val itemLayoutRes: Int
    private var items: MutableList<T> = ArrayList()
    private var currentState = DataState.SUCCESS

    fun setItems(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addItems(newItems: List<T>) {
        val oldItemsCount = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(oldItemsCount, items.size)
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
        return items.size + getExtraRow()
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
                        itemLayoutRes,
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
        if (holder is SearchListAdapter<*>.ItemViewHolder) {
            bindItemToView(items[position], holder.itemView)
        }
    }

    abstract fun bindItemToView(item: T, itemView: View)

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)
}