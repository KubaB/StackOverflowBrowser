package com.jakubbrzozowski.stackoverflowbrowser.ui.base

import android.content.Context
import android.content.res.Resources
import android.support.annotation.StringRes
import org.jetbrains.anko.toast

interface MvpView {
    fun getResources(): Resources
    fun getContext(): Context

    fun showError(@StringRes res: Int) {
        getContext().toast(res)
    }
}