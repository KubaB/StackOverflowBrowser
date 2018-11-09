package com.jakubbrzozowski.stackoverflowbrowser.injection.component

import com.jakubbrzozowski.stackoverflowbrowser.injection.module.ActivityModule
import com.jakubbrzozowski.stackoverflowbrowser.injection.scope.ConfigPersistent
import dagger.Component

@ConfigPersistent
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface ConfigPersistentComponent {
    fun activityComponent(activityModule: ActivityModule): ActivityComponent
}