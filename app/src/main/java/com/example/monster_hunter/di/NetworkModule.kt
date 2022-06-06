package com.example.monster_hunter.di

import com.example.monster_hunter.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun createNetworkClient() =
    retrofitClient(BuildConfig.BASE_URL, okHttpClient())

private fun okHttpClient() = OkHttpClient.Builder()
    .build()

private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

