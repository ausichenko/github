package com.ausichenko.github

import android.app.Application
import com.ausichenko.github.di.githubModule
import com.ausichenko.github.di.networkModule
import org.koin.android.ext.android.startKoin

class GitHubApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(applicationContext, listOf(githubModule, networkModule))
    }
}