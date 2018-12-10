package com.ausichenko.github.view.search.commits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ausichenko.github.R
import com.ausichenko.github.data.network.models.Commit
import kotlinx.android.synthetic.main.item_commit.view.*
import java.util.*

class CommitsAdapter(private val clickListener: (Commit) -> Unit) :
    RecyclerView.Adapter<CommitsAdapter.ViewHolder>() {

    private val commits: MutableList<Commit> = ArrayList()

    fun setItems(items: List<Commit>) {
        commits.clear()
        commits.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<Commit>) {
        commits.addAll(items)
        notifyDataSetChanged() // todo: replace to range changed or inserted
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_commit,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return commits.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(commits[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(commit: Commit) {
            itemView.name.text = commit.commit.message

            val author = commit.author
            val committer = commit.committer

            if (!author.login.equals(committer.login)) {
                itemView.info.text = itemView.context.getString(
                    R.string.commit_info_authored_committed,
                    author.login,
                    committer.login,
                    commit.repository.fullName
                )
            } else {
                itemView.info.text = itemView.context.getString(
                    R.string.commit_info_committed,
                    author.login,
                    commit.repository.fullName
                )
            }

            itemView.setOnClickListener {
                clickListener.invoke(commit)
            }
        }
    }
}