package com.ausichenko.github.view.search.users

import android.view.View
import androidx.core.content.ContextCompat
import com.ausichenko.github.R
import com.ausichenko.github.data.models.User
import com.ausichenko.github.utils.RoundedCornersTransformation
import com.ausichenko.github.view.search.base.SearchListAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(private val clickListener: (User) -> Unit) : SearchListAdapter<User>() {

    override val itemLayoutRes: Int
        get() = R.layout.item_user

    val cornerTransformation = RoundedCornersTransformation(20, 10)

    override fun bindItemToView(item: User, itemView: View) {
        Picasso.get().load(item.avatarUrl).placeholder(
            ContextCompat.getDrawable(
                itemView.context,
                R.drawable.placeholder_user
            )!!
        ).transform(cornerTransformation).into(itemView.avatar)
        itemView.login.text = item.login

        itemView.setOnClickListener {
            clickListener.invoke(item)
        }
    }
}