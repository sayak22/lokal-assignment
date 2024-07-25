package com.example.lokalassignment.model

import com.google.gson.annotations.SerializedName

/**
 * Represents a job retrieved from the API.
 *
 * @param id The unique identifier for the job.
 * @param title The title of the job.
 * @param primaryDetails Additional details related to the job (e.g., place and salary).
 * @param phoneNumber The WhatsApp phone number associated with the job.
 * @param isBookmarked Indicates whether the job is bookmarked by the user (default is false).
 */
data class Job(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String?,
    @SerializedName("primary_details") val primaryDetails: PrimaryDetails?,
    @SerializedName("whatsapp_no") val phoneNumber: String?,
    var isBookmarked: Int = 0
) {
    /**
     * Represents primary details related to the job.
     *
     * @param destination The place associated with the job.
     * @param salary The salary information for the job.
     */
    data class PrimaryDetails(
        @SerializedName("Place") val destination: String?,
        @SerializedName("Salary") val salary: String?
    )
}
