package com.jakubbrzozowski.stackoverflowbrowser.injection.component

import com.jakubbrzozowski.stackoverflowbrowser.injection.module.ActivityModule
import com.jakubbrzozowski.stackoverflowbrowser.injection.scope.PerActivity
import com.jakubbrzozowski.stackoverflowbrowser.ui.main.MainActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}