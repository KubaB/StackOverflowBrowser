package com.jakubbrzozowski.stackoverflowbrowser

import android.app.Application
import com.jakubbrzozowski.stackoverflowbrowser.injection.component.ApplicationComponent
import com.jakubbrzozowski.stackoverflowbrowser.injection.component.DaggerApplicationComponent
import com.jakubbrzozowski.stackoverflowbrowser.injection.module.ApplicationModule
import io.realm.Realm
import io.realm.RealmConfiguration
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
        setupRealm()
    }

    private fun setupRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)
    }
}