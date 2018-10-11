package com.ausichenko.github

import android.app.Application
import android.util.Log

class GitHubApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Log.d("GitHubApplication", "onCreate()")
    }
}