package com.ausichenko.github.utils.ext

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun View.setVisibleOrGone(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun EditText.onTextChangedListener(func: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            func.invoke(text.toString())
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}

fun EditText.setOnSearchListener(func: () -> Unit) {
    setOnEditorActionListener { view, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            view.dismissKeyboard()
            func.invoke()
            true
        } else {
            false
        }
    }
}

fun TextView.dismissKeyboard() {
    clearFocus()
    val imm = (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

const val loadingTriggerThreshold = 5

fun RecyclerView.setLoadMoreListener(func: () -> Unit) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val layoutManager = recyclerView.layoutManager
            if (layoutManager != null) {

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                var firstVisibleItemPosition = 0
                if (layoutManager is LinearLayoutManager) {
                    firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                } else {
                    throw IllegalStateException("layoutManager needs to subclass LinearLayoutManager")
                }

                if ((totalItemCount - visibleItemCount) <= (firstVisibleItemPosition + loadingTriggerThreshold)) {
                    func.invoke()
                }
            }
        }
    })
}