package com.ausichenko.github.di

import android.content.Context
import com.ausichenko.github.BuildConfig
import com.ausichenko.github.data.network.GithubApi
import com.ausichenko.github.data.network.interceptors.NetworkInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { makeGson() }

    single { makeLoggingInterceptor() }
    single { makeNetworkInterceptor(androidContext()) }

    single { makeOkHttpClient(get(), get()) }
    single { makeGitHubService(get(), get()) }
}

fun makeGson(): Gson {
    return GsonBuilder()
        .setLenient()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
}

fun makeLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = if (BuildConfig.DEBUG)
        HttpLoggingInterceptor.Level.BODY
    else
        HttpLoggingInterceptor.Level.NONE
    return logging
}

fun makeNetworkInterceptor(context: Context): NetworkInterceptor {
    return NetworkInterceptor(context)
}

fun makeOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    networkInterceptor: NetworkInterceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(networkInterceptor)
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .build()
}

fun makeGitHubService(okHttpClient: OkHttpClient, gson: Gson): GithubApi {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    return retrofit.create(GithubApi::class.java)
}