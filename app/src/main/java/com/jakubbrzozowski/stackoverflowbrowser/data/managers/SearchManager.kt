package com.jakubbrzozowski.stackoverflowbrowser.data.managers

import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Question
import io.reactivex.Single

interface SearchManager {
    fun searchQuestions(q: String): Single<List<Question?>?>
    fun loadNextPage(q: String): Single<List<Question?>?>
}