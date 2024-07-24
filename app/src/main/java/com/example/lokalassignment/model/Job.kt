package com.example.lokalassignment.model

import com.example.lokalassignment.db.BookmarkedJob
import com.google.gson.annotations.SerializedName

data class Job(
    @SerializedName("id") val id: Long?,
    @SerializedName("title") val title: String?,
    @SerializedName("primary_details") val primaryDetails: PrimaryDetails?,
    @SerializedName("whatsapp_no") val phoneNumber: String?,
    var isBookmarked: Boolean = false
) {
    data class PrimaryDetails(
        @SerializedName("Place") val destination: String?,
        @SerializedName("Salary") val salary: String?
    )
}


