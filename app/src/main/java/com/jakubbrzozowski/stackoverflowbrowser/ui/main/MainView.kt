package com.jakubbrzozowski.stackoverflowbrowser.ui.main

import com.jakubbrzozowski.stackoverflowbrowser.ui.base.MvpView

interface MainView : MvpView {

    fun getQueryString(): String
}