package com.jakubbrzozowski.stackoverflowbrowser.ui.base

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<T : MvpView> : BasePresenterInterface<T> {

    val subsciprtions = CompositeDisposable()
    val allSubscriptions = arrayListOf(subsciprtions)

    private var _view: T? = null
    val view: T
        get() {
            return _view ?: throw IllegalStateException()
        }

    override fun attachView(view: T) {
        _view = view
        clearAllSubsciptions()
    }

    override fun detachView() {
        _view = null
        clearAllSubsciptions()
    }

    private fun clearAllSubsciptions() {
        allSubscriptions.forEach { it.clear() }
    }
}