package com.ausichenko.github.view.search.repositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ausichenko.github.R
import com.ausichenko.github.data.network.models.GitRepository
import kotlinx.android.synthetic.main.item_repository.view.*
import java.util.ArrayList

class RepositoriesAdapter(private val clickListener: (GitRepository) -> Unit) : RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>() {

    private val repositories: MutableList<GitRepository> = ArrayList()

    fun setItems(items: List<GitRepository>) {
        repositories.clear()
        repositories.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<GitRepository>) {
        repositories.addAll(items)
        notifyDataSetChanged() // todo: replace to range changed or inserted
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repository, parent, false))
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(repository: GitRepository) {
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
            if (repository.stargazersCount > 0) {
                itemView.starsLayout.visibility = View.VISIBLE
                itemView.stars.text = repository.stargazersCount.toString()
            } else {
                itemView.starsLayout.visibility = View.GONE
            }

            itemView.setOnClickListener {
                clickListener.invoke(repository)
            }
        }
    }
}