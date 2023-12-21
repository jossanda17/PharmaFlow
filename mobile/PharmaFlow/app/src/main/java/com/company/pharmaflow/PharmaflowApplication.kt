package com.company.pharmaflow

import android.app.Application
import com.company.pharmaflow.data.AppContainer
import com.company.pharmaflow.data.AppDataContainer
import com.company.pharmaflow.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PharmaflowApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        startKoin {
            androidContext(this@PharmaflowApplication)
            androidLogger()
            modules(appModule)
        }
    }
}