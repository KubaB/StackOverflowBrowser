package com.jakubbrzozowski.stackoverflowbrowser.ui.base

interface BasePresenterInterface<in V : MvpView> {
    fun attachView(view: V)
    fun detachView()
}