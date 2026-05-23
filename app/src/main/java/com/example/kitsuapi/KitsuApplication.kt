package com.example.kitsuapi

import android.app.Application
import com.example.kitsuapi.di.AppContainer

class KitsuApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer()
    }
}
