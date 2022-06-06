package com.example.monster_hunter.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.monster_hunter.data.repositories.ArmorRepository
import com.example.monster_hunter.data.repositories.ArmorRepositoryImpl
import com.example.monster_hunter.data.repositories.FavArmorRepository
import com.example.monster_hunter.data.repositories.FavArmorRepositoryImpl
import com.example.monster_hunter.retrofit.WebServices
import com.example.monster_hunter.views.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { HomeViewModel(armorRepository = get(), favArmorRepository = get()) }
}

val repositoryModule = module {
    single<ArmorRepository> { ArmorRepositoryImpl(api = get()) } bind ArmorRepository::class
    single<FavArmorRepository> { FavArmorRepositoryImpl(sharedPreferences = get()) } bind FavArmorRepository::class
}
val networkModule = module {
    single {
        createNetworkClient()
            .create(WebServices::class.java)
    }
}

val preferencesModule = module {
    single { provideSharedPrefs(androidApplication()) }
}


fun provideSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE)
}