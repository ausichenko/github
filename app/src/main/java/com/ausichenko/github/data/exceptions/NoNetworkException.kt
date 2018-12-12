package com.ausichenko.github.data.exceptions

import com.ausichenko.github.R

class NoNetworkException :
    GitHubException(R.drawable.ic_no_network, R.string.no_network_error)