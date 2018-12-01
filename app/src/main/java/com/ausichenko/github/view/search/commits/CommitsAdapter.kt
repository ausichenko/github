package com.ausichenko.github.view.search.commits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ausichenko.github.R
import com.ausichenko.github.data.network.models.GitCommit
import kotlinx.android.synthetic.main.item_commit.view.*
import java.text.SimpleDateFormat
import java.util.*

class CommitsAdapter(private val clickListener: (GitCommit) -> Unit) : RecyclerView.Adapter<CommitsAdapter.ViewHolder>() {

    private val commits: MutableList<GitCommit> = ArrayList()

    fun setItems(items: List<GitCommit>) {
        commits.clear()
        commits.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<GitCommit>) {
        commits.addAll(items)
        notifyDataSetChanged() // todo: replace to range changed or inserted
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_commit, parent, false))
    }

    override fun getItemCount(): Int {
        return commits.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(commits[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(commit: GitCommit) {
            itemView.name.text = commit.commit.message

            val author = commit.author.login
            val commiter = commit.commiter.login

            var info = author
            if (commiter.equals(author)) {
                info = info.plus(" authored and ").plus(commiter)
            }
            var format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            val newDate = format.parse(commit.commit.commiter.date)

            format = SimpleDateFormat("MMM dd")
            val date = format.format(newDate)

            info = info.plus(" committed to ").plus(commit.repository.fullName).plus(" on ").plus(date)

            // todo: to res

            itemView.info.text = info

            itemView.setOnClickListener {
                clickListener.invoke(commit)
            }
        }
    }
}