package com.jakubbrzozowski.stackoverflowbrowser

import android.app.Application
import com.jakubbrzozowski.stackoverflowbrowser.injection.component.ApplicationComponent
import com.jakubbrzozowski.stackoverflowbrowser.injection.component.DaggerApplicationComponent
import com.jakubbrzozowski.stackoverflowbrowser.injection.module.ApplicationModule
import timber.log.Timber

class MyApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}