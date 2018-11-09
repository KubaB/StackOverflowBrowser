package com.jakubbrzozowski.stackoverflowbrowser.injection.component

import android.app.Application
import android.content.Context
import com.jakubbrzozowski.stackoverflowbrowser.data.remote.ApiService
import com.jakubbrzozowski.stackoverflowbrowser.injection.module.ApiModule
import com.jakubbrzozowski.stackoverflowbrowser.injection.module.ApplicationModule
import com.jakubbrzozowski.stackoverflowbrowser.injection.qualifier.ApplicationContext
import com.jakubbrzozowski.stackoverflowbrowser.injection.qualifier.MainScheduler
import dagger.Component
import io.reactivex.Scheduler
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, ApiModule::class))
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application
    fun apiService(): ApiService
    @MainScheduler
    fun scheduler(): Scheduler
}