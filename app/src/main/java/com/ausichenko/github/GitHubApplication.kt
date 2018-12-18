package com.ausichenko.github

import android.app.Application
import com.ausichenko.github.di.databaseModule
import com.ausichenko.github.di.githubModule
import com.ausichenko.github.di.networkModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.startKoin

class GitHubApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(applicationContext)
        startKoin(applicationContext, listOf(githubModule, databaseModule, networkModule))
    }
}