package com.jakubbrzozowski.stackoverflowbrowser.data.repository

import com.jakubbrzozowski.stackoverflowbrowser.data.model.realm.RealmQuestion
import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Question
import io.realm.Realm
import java.util.*

class QuestionsRepositoryImpl : QuestionsRepository {
    override fun getAllQuestions(): List<Question?> {
        var questions: List<Question> = Collections.emptyList()
        Realm.getDefaultInstance().use { realm ->
            questions = realm.where(RealmQuestion::class.java).findAll().map { it.toQuestion() }
        }
        return questions
    }

    override fun saveQuestions(questions: List<Question?>) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                it.copyToRealm(questions.map { question -> RealmQuestion.from(question) })
            }
        }
    }

    override fun deleteAllQuestions() {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                it.delete(RealmQuestion::class.java)
            }
        }
    }
}