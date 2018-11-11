package com.jakubbrzozowski.stackoverflowbrowser.data.model.remote

import com.google.gson.annotations.SerializedName

data class Owner(

	@field:SerializedName("profile_image")
	val profileImage: String? = null,

	@field:SerializedName("user_type")
	val userType: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("reputation")
	val reputation: Int? = null,

	@field:SerializedName("display_name")
	val displayName: String? = null,

	@field:SerializedName("accept_rate")
	val acceptRate: Int? = null
)