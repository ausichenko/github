package com.ausichenko.github.data.exceptions

import androidx.annotation.StringRes

abstract class MessageException(@StringRes val errorMessage: Int) :
    Exception()