package com.jakubbrzozowski.stackoverflowbrowser.data.managers

import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Question
import com.jakubbrzozowski.stackoverflowbrowser.data.remote.ApiService
import com.jakubbrzozowski.stackoverflowbrowser.data.repository.QuestionsRepository
import com.jakubbrzozowski.stackoverflowbrowser.utils.PositionAndCount
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
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

    private var searchResults: MutableList<Question?> = Collections.emptyList()
    private var pageCounter = STARTING_PAGE

    override fun searchQuestions(q: String): Single<List<Question?>?> {
        pageCounter = STARTING_PAGE
        searchResults.clear()
        return searchQuestions(q, STARTING_PAGE)
    }

    override fun loadNextPage(q: String): Single<PositionAndCount> {
        return Single.create { emitter ->
            val lastPosition = searchResults.size - 1
            searchQuestions(q, ++pageCounter)
                    .subscribe(
                            {
                                emitter.onSuccess(PositionAndCount(
                                        lastPosition,
                                        searchResults.size - lastPosition - 1))
                            },
                            { ex -> Timber.e(ex) })
        }
    }

    private fun searchQuestions(q: String, page: Int): Single<List<Question?>?> {
        return apiService.searchQuestions(q, DEFAULT_SITE, page)
                .map { questions ->
                    if (page == STARTING_PAGE) questionsRepository.deleteAllQuestions()
                    questions.items?.let { questionsRepository.saveQuestions(it) }
                    questionsRepository.getAllQuestions()
                }
                .subscribeOn(Schedulers.io())
    }
}