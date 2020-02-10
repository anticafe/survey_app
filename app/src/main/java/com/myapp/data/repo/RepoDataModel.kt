package com.myapp.data.repo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SurveyItem(
  val id: String = "",
  val title: String = "",
  val description: String = "",
  val coverImageUrl: String = ""
) : Parcelable {
  // Get the high resolution version of cover image url
  val coverHighResImageUrl = if (coverImageUrl.isEmpty()) "" else coverImageUrl + "l"
}

data class AccessTokenItem(
  val accessToken: String = "",
  val tokenType: String = "",
  val expiresIn: Int = 0,
  val createdAt: Int = 0
) {
  // Get the Bearer Token from an accessToken
  val bearerToken = "Bearer ${this.accessToken}"
}