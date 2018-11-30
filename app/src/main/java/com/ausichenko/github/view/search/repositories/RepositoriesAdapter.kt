package com.ausichenko.github.view.search.repositories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ausichenko.github.R
import com.ausichenko.github.data.network.models.GitRepository
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoriesAdapter(private val repositories: List<GitRepository>) : RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>() {

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
            itemView.language.text = repository.language
            itemView.description.text = repository.description
            itemView.stars.text = repository.stargazersCount.toString()
        }
    }
}