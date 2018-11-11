package com.jakubbrzozowski.stackoverflowbrowser.data.repository

import com.jakubbrzozowski.stackoverflowbrowser.data.model.realm.RealmQuestion
import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Question
import io.realm.Realm
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionsRepository @Inject constructor() {
    fun getAllQuestions(): List<Question?> {
        var questions: List<Question> = Collections.emptyList()
        Realm.getDefaultInstance().use { realm ->
            questions = realm.where(RealmQuestion::class.java).findAll().map { it.toQuestion() }
        }
        return questions
    }

    fun saveQuestions(questions: List<Question?>) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                it.copyToRealm(questions.map { question -> RealmQuestion.from(question) })
            }
        }
    }

    fun deleteAllQuestions() {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                it.delete(RealmQuestion::class.java)
            }
        }
    }
}