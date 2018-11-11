package com.jakubbrzozowski.stackoverflowbrowser.ui.main

import com.jakubbrzozowski.stackoverflowbrowser.R
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
        if (lastQueryText == searchQuery.toString()) {
            view.showRefreshing(false)
        } else {
            lastQueryText = searchQuery.toString()
            reloadSearchResults(searchQuery)
        }
    }

    private fun reloadSearchResults(searchQuery: String?) {
        if (!searchQuery.isNullOrBlank()) {
            subsciprtions.clear()
            subsciprtions.add(
                    searchManager.searchQuestions(searchQuery)
                            .observeOn(mainScheduler)
                            .subscribe(
                                    { it ->
                                        showQuestions(it)
                                        view.showRefreshing(false)
                                    },
                                    { ex ->
                                        view.showRefreshing(false)
                                        view.showError(R.string.err_data_loading)
                                        Timber.e(ex)
                                    }))
        } else {
            showQuestions(Collections.emptyList())
            view.showRefreshing(false)
        }
    }

    private fun showQuestions(questions: List<Question?>?) {
        questions?.let {
            lastQuestionsList = it
            view.showQuestions(it)
        }
    }

    fun onRefresh() {
        view.showRefreshing(true)
        reloadSearchResults(view.getQueryString())
    }

    fun questionClicked(questionId: Int?) {
        questionId?.let { view.openQuestionDetails(it) }
    }

    fun endOfListReached() {
        val queryString = view.getQueryString()
        if (!queryString.isBlank()) {
            subsciprtions.add(searchManager.loadNextPage(queryString)
                    .observeOn(mainScheduler)
                    .subscribe(
                            { it -> showQuestions(it) },
                            { ex -> Timber.e(ex) })
            )
        }
    }
}