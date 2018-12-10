package com.ausichenko.github.view.search.issues

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ausichenko.github.R
import com.ausichenko.github.data.network.models.Issue
import kotlinx.android.synthetic.main.item_issue.view.*
import java.util.*

class IssuesAdapter(private val clickListener: (Issue) -> Unit) :
    RecyclerView.Adapter<IssuesAdapter.ViewHolder>() {

    private val issues: MutableList<Issue> = ArrayList()

    fun setItems(items: List<Issue>) {
        issues.clear()
        issues.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Issue>) {
        issues.addAll(items)
        notifyDataSetChanged() // todo: replace to range changed or inserted
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_issue,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return issues.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(issues[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(issue: Issue) {
            itemView.title.text = issue.title
            itemView.body.text = issue.body
            itemView.number.text = itemView.context.getString(R.string.number, issue.number)

            itemView.setOnClickListener {
                clickListener.invoke(issue)
            }
        }
    }
}