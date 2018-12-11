package com.ausichenko.github.utils.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter

@BindingAdapter("drawableRes")
fun ImageView.setDrawableRes(@DrawableRes drawableRes: Int) {
    if (drawableRes != 0) {
        setImageResource(drawableRes)
    }
}