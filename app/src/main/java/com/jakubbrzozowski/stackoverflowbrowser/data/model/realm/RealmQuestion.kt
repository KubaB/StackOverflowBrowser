package com.jakubbrzozowski.stackoverflowbrowser.data.model.realm

import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Question
import io.realm.RealmList
import io.realm.RealmObject

open class RealmQuestion(
        var owner: RealmOwner?,
        var score: Int?,
        var link: String?,
        var lastActivityDate: Int?,
        var isAnswered: Boolean?,
        var creationDate: Int?,
        var answerCount: Int?,
        var title: String?,
        var questionId: Int?,
        var viewCount: Int?,
        var tags: RealmList<String?>?,
        var lastEditDate: Int?,
        var acceptedAnswerId: Int?,
        var bountyAmount: Int?,
        var bountyClosesDate: Int?,
        var closedDate: Int?,
        var closedReason: String?
) : RealmObject() {
    constructor() : this(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    )

    companion object {
        fun from(question: Question?): RealmQuestion {
            return RealmQuestion(owner = RealmOwner.from(question?.owner),
                    score = question?.score,
                    link = question?.link,
                    lastActivityDate = question?.lastActivityDate,
                    isAnswered = question?.isAnswered,
                    creationDate = question?.creationDate,
                    answerCount = question?.answerCount,
                    title = question?.title,
                    questionId = question?.questionId,
                    viewCount = question?.viewCount,
                    tags = RealmList(*(question?.tags?.toTypedArray() ?: arrayOf(""))),
                    lastEditDate = question?.lastEditDate,
                    acceptedAnswerId = question?.acceptedAnswerId,
                    bountyAmount = question?.bountyAmount,
                    bountyClosesDate = question?.bountyClosesDate,
                    closedDate = question?.closedDate,
                    closedReason = question?.closedReason)
        }
    }

    fun toQuestion(): Question {
        return Question(
                owner = owner?.toOwner(),
                score = score,
                link = link,
                lastActivityDate = lastActivityDate,
                isAnswered = isAnswered,
                creationDate = creationDate,
                answerCount = answerCount,
                title = title,
                questionId = questionId,
                viewCount = viewCount,
                tags = tags,
                lastEditDate = lastEditDate,
                acceptedAnswerId = acceptedAnswerId,
                bountyAmount = bountyAmount,
                bountyClosesDate = bountyClosesDate,
                closedDate = closedDate,
                closedReason = closedReason)
    }
}