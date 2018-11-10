package com.jakubbrzozowski.stackoverflowbrowser.data.managers

import com.jakubbrzozowski.stackoverflowbrowser.data.model.Question
import com.jakubbrzozowski.stackoverflowbrowser.data.remote.ApiService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class SearchManager @Inject
constructor(private val apiService: ApiService) {
    companion object {
        const val DEFAULT_SITE = "stackoverflow"
    }

    open fun searchQuestions(q: String, page: Int): Single<List<Question?>?> {
        return apiService.searchQuestions(q, DEFAULT_SITE, page)
                .map { questions -> questions.items }
                .subscribeOn(Schedulers.io())
    }
}