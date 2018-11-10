package com.jakubbrzozowski.stackoverflowbrowser.ui.questiondetails

import com.jakubbrzozowski.stackoverflowbrowser.ui.base.MvpView

interface QuestionDetailsView : MvpView {

    fun setWebViewUrl(url: String)
}