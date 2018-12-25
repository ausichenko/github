package com.ausichenko.github.utils.livedata

class State<Data> {
    var state = DataState.INIT
    var data: Data? = null
    var error: Throwable? = null


}

