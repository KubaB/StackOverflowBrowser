package com.jakubbrzozowski.stackoverflowbrowser.data.remote

import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Questions
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        private const val API_VERSION = "2.2"
        const val URL = "http://api.stackexchange.com/$API_VERSION/"
        private const val QUESTION_URL = "https://stackoverflow.com/questions/"
        fun getQuestionUrl(questionId: Int): String {
            return QUESTION_URL + questionId
        }
    }

    @GET("search")
    fun searchQuestions(
            @Query("intitle") intitle: String,
            @Query("site") site: String,
            @Query("page") page: Int
    ): Single<Questions>
}
