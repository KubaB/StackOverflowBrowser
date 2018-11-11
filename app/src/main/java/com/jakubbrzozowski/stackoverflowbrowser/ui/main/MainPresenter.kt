package com.jakubbrzozowski.stackoverflowbrowser.ui.main

import com.jakubbrzozowski.stackoverflowbrowser.data.managers.SearchManager
import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Question
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

    private var lastQueryText: String = ""
    private var lastQuestionsList: List<Question?> = Collections.emptyList()

    override fun attachView(view: MainView) {
        super.attachView(view)
        view.showQuestions(lastQuestionsList)
    }

    fun searchFieldChanged(searchQuery: String?) {
        if (!searchQuery.isNullOrBlank()) {
            subsciprtions.clear()
            subsciprtions.add(
                    searchManager.searchQuestions(searchQuery)
                            .observeOn(mainScheduler)
                            .subscribe(
                                    { list ->
                                        list?.let {
                                            showQuestions(it, searchQuery)
                                            view.showRefreshing(false)
                                        }
                                    },
                                    { ex ->
                                        view.showRefreshing(false)
                                        Timber.e(ex)
                                    }))
        } else {
            showQuestions(Collections.emptyList(), searchQuery.toString())
            view.showRefreshing(false)
        }
    }

    private fun showQuestions(questions: List<Question?>, queryText: String) {
        lastQuestionsList = questions
        if (lastQueryText != queryText) view.showQuestions(questions)
        lastQueryText = queryText
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