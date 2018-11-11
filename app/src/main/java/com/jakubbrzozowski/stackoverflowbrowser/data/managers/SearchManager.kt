package com.jakubbrzozowski.stackoverflowbrowser.data.managers

import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Question
import com.jakubbrzozowski.stackoverflowbrowser.utils.PositionAndCount
import io.reactivex.Single

interface SearchManager {
    fun searchQuestions(q: String): Single<List<Question?>?>
    fun loadNextPage(q: String): Single<PositionAndCount>
}