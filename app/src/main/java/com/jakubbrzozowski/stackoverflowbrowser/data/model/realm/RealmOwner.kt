package com.jakubbrzozowski.stackoverflowbrowser.data.model.realm

import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Owner
import io.realm.RealmObject

open class RealmOwner(
        var profileImage: String?,
        var userType: String?,
        var userId: Int?,
        var link: String?,
        var reputation: Int?,
        var displayName: String?,
        var acceptRate: Int?
) : RealmObject() {
    constructor() : this(
            null,
            null,
            null,
            null,
            null,
            null,
            null
    )

    companion object {
        fun from(owner: Owner?): RealmOwner {
            return RealmOwner(profileImage = owner?.profileImage,
                    userType = owner?.userType,
                    userId = owner?.userId,
                    link = owner?.link,
                    reputation = owner?.reputation,
                    displayName = owner?.displayName,
                    acceptRate = owner?.acceptRate)
        }
    }

    fun toOwner(): Owner {
        return Owner(profileImage = profileImage,
                userType = userType,
                userId = userId,
                link = link,
                reputation = reputation,
                displayName = displayName,
                acceptRate = acceptRate)
    }
}