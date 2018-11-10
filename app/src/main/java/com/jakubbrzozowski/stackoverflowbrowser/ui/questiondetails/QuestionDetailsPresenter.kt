package com.jakubbrzozowski.stackoverflowbrowser.ui.questiondetails

import com.jakubbrzozowski.stackoverflowbrowser.data.remote.ApiService
import com.jakubbrzozowski.stackoverflowbrowser.injection.qualifier.MainScheduler
import com.jakubbrzozowski.stackoverflowbrowser.injection.scope.ConfigPersistent
import com.jakubbrzozowski.stackoverflowbrowser.ui.base.BasePresenter
import io.reactivex.Scheduler
import javax.inject.Inject

@ConfigPersistent
class QuestionDetailsPresenter
@Inject
constructor(@MainScheduler private val mainScheduler: Scheduler) : BasePresenter<QuestionDetailsView>() {

    fun questionIdReceived(questionId: Int) {
        view.setWebViewUrl(ApiService.getQuestionUrl(questionId))
    }

}