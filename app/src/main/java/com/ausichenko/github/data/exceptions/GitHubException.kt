package com.ausichenko.github.data.exceptions

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ausichenko.github.R

abstract class GitHubException(@DrawableRes val errorImage: Int, @StringRes val errorMessage: Int) :
    Exception() {
    constructor() : this(R.drawable.ic_error, R.string.error_while_loading)
}