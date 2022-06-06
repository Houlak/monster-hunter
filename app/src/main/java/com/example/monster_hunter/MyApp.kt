package com.example.monster_hunter

import android.app.Application
import com.example.monster_hunter.di.networkModule
import com.example.monster_hunter.di.preferencesModule
import com.example.monster_hunter.di.repositoryModule
import com.example.monster_hunter.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {

    companion object {
        @JvmField
        var appInstance: MyApp? = null

        @JvmStatic
        fun getAppInstance(): MyApp {
            return appInstance as MyApp
        }
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    repositoryModule,
                    preferencesModule
                )
            )
        }
    }
}