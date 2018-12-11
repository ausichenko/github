package com.ausichenko.github.data.exceptions

import com.ausichenko.github.R

class EmptySearchException :
    GitHubException(R.drawable.ic_search, R.string.empty_search_error)