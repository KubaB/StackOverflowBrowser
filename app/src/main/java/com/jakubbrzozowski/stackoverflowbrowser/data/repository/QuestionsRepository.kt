package com.jakubbrzozowski.stackoverflowbrowser.data.repository

import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Question

interface QuestionsRepository {
    fun getAllQuestions(): List<Question?>
    fun saveQuestions(questions: List<Question?>)
    fun deleteAllQuestions()
}