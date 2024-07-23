package com.example.lokalassignment.model

import com.google.gson.annotations.SerializedName

data class Job(
    @SerializedName("title") val title: String?,
    @SerializedName("primary_details") val primaryDetails: PrimaryDetails?,
    @SerializedName("whatsapp_no") val whatsappNumber: String?
) {
    data class PrimaryDetails(
        @SerializedName("Place") val place: String?,
        @SerializedName("Salary") val salary: String?
    )
}


