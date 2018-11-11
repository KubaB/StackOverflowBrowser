package com.jakubbrzozowski.stackoverflowbrowser.ui.main

import com.jakubbrzozowski.stackoverflowbrowser.data.managers.SearchManager
import com.jakubbrzozowski.stackoverflowbrowser.injection.qualifier.MainScheduler
import com.jakubbrzozowski.stackoverflowbrowser.injection.scope.ConfigPersistent
import com.jakubbrzozowski.stackoverflowbrowser.ui.base.BasePresenter
import io.reactivex.Scheduler
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@ConfigPersistent
class MainPresenter
@Inject
constructor(private val searchManager: SearchManager,
            @MainScheduler private val mainScheduler: Scheduler) : BasePresenter<MainView>() {

    fun searchFieldChanged(searchQuery: String?) {
        if (!searchQuery.isNullOrBlank()) {
            subsciprtions.clear()
            subsciprtions.add(
                    searchManager.searchQuestions(searchQuery.toString())
                            .observeOn(mainScheduler)
                            .subscribe(
                                    { list ->
                                        list?.let {
                                            view.showQuestions(it)
                                            view.showRefreshing(false)
                                        }
                                    },
                                    { ex ->
                                        view.showRefreshing(false)
                                        Timber.e(ex)
                                    }))
        } else {
            view.showRefreshing(false)
        }
    }

    fun onRefresh() {
        view.showRefreshing(true)
        searchFieldChanged(view.getQueryString())
    }

    fun questionClicked(questionId: Int?) {
        questionId?.let { view.openQuestionDetails(it) }
    }

    fun endOfListReached() {

    }
}