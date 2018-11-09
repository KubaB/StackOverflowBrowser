package com.jakubbrzozowski.stackoverflowbrowser.ui.main

import com.jakubbrzozowski.stackoverflowbrowser.injection.qualifier.MainScheduler
import com.jakubbrzozowski.stackoverflowbrowser.injection.scope.ConfigPersistent
import com.jakubbrzozowski.stackoverflowbrowser.ui.base.BasePresenter
import io.reactivex.Scheduler
import javax.inject.Inject

@ConfigPersistent
class MainPresenter
@Inject
constructor(@MainScheduler private val mainScheduler: Scheduler) : BasePresenter<MainView>() {


}