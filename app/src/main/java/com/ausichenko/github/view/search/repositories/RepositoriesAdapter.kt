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
import com.ausichenko.github.utils.ext.loge
import kotlinx.android.synthetic.main.item_repository.view.*


class RepositoriesAdapter(private val clickListener: (Repository) -> Unit) :
    PagedListAdapter<Repository, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        val diffCallback: DiffUtil.ItemCallback<Repository> =
            object : DiffUtil.ItemCallback<Repository>() {
                override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RepositoryViewHolder) {
            getItem(position)?.let { repository -> holder.bind(repository) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepositoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_repository,
                parent,
                false
            )
        )
    }

    inner class RepositoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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

    inner class NetworkStateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(networkState: DataState?) {
            if (networkState != null && networkState === DataState.Loading) {
                loge("LOADING")
                //binding.progressBar.setVisibility(View.VISIBLE)
            } else {
                //binding.progressBar.setVisibility(View.GONE)
            }

            if (networkState != null && networkState === DataState.Error) {
                loge("ERROR")
                //binding.errorMsg.setVisibility(View.VISIBLE)
                //binding.errorMsg.setText(networkState.getMsg())
            } else {
                //binding.errorMsg.setVisibility(View.GONE)
            }
        }
    }
}