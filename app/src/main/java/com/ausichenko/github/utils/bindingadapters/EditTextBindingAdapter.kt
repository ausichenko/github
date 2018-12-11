package com.ausichenko.github.utils.bindingadapters

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.ausichenko.github.utils.ext.dismissKeyboard

@BindingAdapter("onSearch")
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