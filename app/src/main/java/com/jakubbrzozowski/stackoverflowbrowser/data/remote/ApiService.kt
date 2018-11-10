package com.jakubbrzozowski.stackoverflowbrowser.data.remote

import com.jakubbrzozowski.stackoverflowbrowser.data.model.Questions
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        private const val API_VERSION = "2.2"
        const val URL = "http://api.stackexchange.com/$API_VERSION/"
    }

    @GET("/search")
    fun searchQuestions(
            @Query("intitle") intitle: String,
            @Query("site") site: String,
            @Query("page") page: Int
    ): Single<Questions>
}
