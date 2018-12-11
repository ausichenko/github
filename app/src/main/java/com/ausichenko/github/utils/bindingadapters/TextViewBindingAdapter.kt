package com.ausichenko.github.utils.bindingadapters

import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter

@BindingAdapter("stringRes")
fun TextView.setStringRes(@StringRes stringRes: Int) {
    if (stringRes != 0) {
        setText(stringRes)
    }
}