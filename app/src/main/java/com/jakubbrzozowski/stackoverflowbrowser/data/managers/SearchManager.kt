package com.jakubbrzozowski.stackoverflowbrowser.data.managers

import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Question
import io.reactivex.subjects.PublishSubject

interface SearchManager {
    var searchResults: PublishSubject<List<Question?>>
    fun newQuestionSearch(q: String)
    fun loadNextPage(q: String)
}