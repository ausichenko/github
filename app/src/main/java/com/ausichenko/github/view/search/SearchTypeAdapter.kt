package com.ausichenko.github.view.search

import android.content.Context
import android.widget.ArrayAdapter
import com.ausichenko.github.R

class SearchTypeAdapter(context: Context, items: List<SearchTypeAdapter.SearchItem>) :
    ArrayAdapter<SearchTypeAdapter.SearchItem>(
        context, R.layout.item_dropdown_spinner, items
    ) {

    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?: -1
    }

    data class SearchItem(var id: Long, var name: String) {
        override fun toString(): String {
            return name
        }
    }
}