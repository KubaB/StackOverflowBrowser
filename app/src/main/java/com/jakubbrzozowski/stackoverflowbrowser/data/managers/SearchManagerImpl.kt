package com.jakubbrzozowski.stackoverflowbrowser.data.managers

import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Question
import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Questions
import com.jakubbrzozowski.stackoverflowbrowser.data.remote.ApiService
import com.jakubbrzozowski.stackoverflowbrowser.data.repository.QuestionsRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchManagerImpl @Inject
constructor(private val apiService: ApiService,
            private val questionsRepository: QuestionsRepository) : SearchManager {

    companion object {
        const val DEFAULT_SITE = "stackoverflow"
        const val STARTING_PAGE = 1
    }

    override val searchResults: PublishSubject<List<Question?>> = PublishSubject.create()
    private val subscriptions = CompositeDisposable()
    private var lastSearchResults: Questions? = null
    private var pageCounter = STARTING_PAGE

    override fun newQuestionSearch(q: String) {
        pageCounter = STARTING_PAGE
        lastSearchResults = null
        subscriptions.clear()
        return searchQuestions(q, STARTING_PAGE)
    }

    override fun loadNextPage(q: String) {
        if (lastSearchResults?.hasMore != false) {
            searchQuestions(q, ++pageCounter)
        }
    }

    private fun searchQuestions(q: String, page: Int) {
        subscriptions.add(apiService.searchQuestions(q, DEFAULT_SITE, page)
                .map { questions ->
                    lastSearchResults = questions
                    if (page == STARTING_PAGE) questionsRepository.deleteAllQuestions()
                    questions.items?.let { questionsRepository.saveQuestions(it) }
                    questionsRepository.getAllQuestions()

                }
                .subscribeOn(Schedulers.io())
                .subscribe(searchResults::onNext, searchResults::onError))
    }
}