package com.ausichenko.github.view.search.topics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ausichenko.github.R
import com.ausichenko.github.data.network.models.Topic
import kotlinx.android.synthetic.main.item_topic.view.*
import java.util.*

class TopicsAdapter(private val clickListener: (Topic) -> Unit) :
    RecyclerView.Adapter<TopicsAdapter.ViewHolder>() {

    private val repositories: MutableList<Topic> = ArrayList()

    fun setItems(items: List<Topic>) {
        repositories.clear()
        repositories.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Topic>) {
        repositories.addAll(items)
        notifyDataSetChanged() // todo: replace to range changed or inserted
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_topic,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
}