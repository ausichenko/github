package com.ausichenko.github

import android.app.Application
import com.ausichenko.github.di.githubModule
import org.koin.android.ext.android.startKoin

class GitHubApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(applicationContext, listOf(githubModule))
    }
}