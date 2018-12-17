package com.ausichenko.github.data.exceptions

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

abstract class FullscreenException(@DrawableRes val errorImage: Int, @StringRes val errorMessage: Int) :
    Exception()