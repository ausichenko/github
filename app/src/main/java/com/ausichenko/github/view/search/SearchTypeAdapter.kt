package com.ausichenko.github.view.search

import android.content.Context
import android.widget.ArrayAdapter

class SearchTypeAdapter(context: Context, items: List<SearchTypeAdapter.SearchItem>) :
    ArrayAdapter<SearchTypeAdapter.SearchItem>(
        context, android.R.layout.simple_spinner_dropdown_item, items
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