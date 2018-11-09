package com.jakubbrzozowski.stackoverflowbrowser.injection.module

import android.app.Activity
import android.content.Context
import com.jakubbrzozowski.stackoverflowbrowser.injection.scope.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @PerActivity
    internal fun provideActivity(): Activity {
        return activity
    }

    @Provides
    @PerActivity
    internal fun provideContext(): Context {
        return activity
    }
}