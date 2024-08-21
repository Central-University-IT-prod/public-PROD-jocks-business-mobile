package ru.jocks.swipecsadbusiness

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.jocks.data.di.dataModule
import ru.jocks.swipecsadbusiness.di.presentationModule
import timber.log.Timber

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)

            modules(dataModule, presentationModule)
        }
        Timber.plant(Timber.DebugTree())
    }
}