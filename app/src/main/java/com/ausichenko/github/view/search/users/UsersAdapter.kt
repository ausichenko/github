package com.ausichenko.github.view.search.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ausichenko.github.R
import com.ausichenko.github.data.network.models.User
import kotlinx.android.synthetic.main.item_user.view.*
import java.util.*

class UsersAdapter(private val clickListener: (User) -> Unit) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private val repositories: MutableList<User> = ArrayList()

    fun setItems(items: List<User>) {
        repositories.clear()
        repositories.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<User>) {
        repositories.addAll(items)
        notifyDataSetChanged() // todo: replace to range changed or inserted
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user,
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
        fun bind(user: User) {
            itemView.username.text = user.login

            itemView.setOnClickListener {
                clickListener.invoke(user)
            }
        }
    }
}