package com.ausichenko.github.view.search.topics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ausichenko.github.R
import com.ausichenko.github.data.models.Topic
import com.ausichenko.github.utils.livedata.DataState
import kotlinx.android.synthetic.main.item_topic.view.*
import java.util.*

class TopicsAdapter(private val clickListener: (Topic) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_ITEM = 1
        private const val TYPE_LOADING = 2
    }

    private val topics: MutableList<Topic> = ArrayList()
    private var currentState = DataState.SUCCESS

    fun setItems(items: List<Topic>) {
        topics.clear()
        topics.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Topic>) {
        val oldItemsCount = topics.size
        topics.addAll(items)
        notifyItemRangeInserted(oldItemsCount, topics.size)
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
        return topics.size + getExtraRow()
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
                        R.layout.item_topic,
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
            holder.bind(topics[position])
        }
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(topic: Topic) {
            itemView.name.text = topic.name

            if (!topic.shortDescription.isNullOrEmpty()) {
                itemView.description.text = topic.shortDescription
                itemView.description.visibility = View.VISIBLE
            } else {
                itemView.description.visibility = View.GONE
            }

            itemView.setOnClickListener {
                clickListener.invoke(topic)
            }
        }
    }

    inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)
}