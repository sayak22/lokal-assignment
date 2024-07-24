package com.example.lokalassignment.model

/**
 * Represents the API response containing a result of type [T].
 *
 * @param results The actual data returned by the API.
 * @param T The type of data, in this case a list of Jobs.
 */
data class ApiResponse<T>(
    val results: T
)
