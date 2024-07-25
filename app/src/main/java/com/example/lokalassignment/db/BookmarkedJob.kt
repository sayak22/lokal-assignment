package com.example.lokalassignment.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing a bookmarked job entity in the database.
 *
 * @property id Unique identifier for the job, used as the primary key.
 * @property title The title of the job, nullable.
 * @property destination The location of the job, nullable.
 * @property salary The salary information for the job, nullable.
 * @property phoneNumber The contact number for the job, nullable.
 * @property isBookmarked Indicator for bookmark status, where 1 means bookmarked.
 */
@Entity(tableName = "bookmarkedJobs")
data class BookmarkedJob(

    @PrimaryKey(autoGenerate = false) // Primary key for the entity, no auto-generation
    val id: Long,

    val title: String?, // Job title, can be null if not provided
    val destination: String?, // Job location, can be null if not provided
    val salary: String?, // Salary details, can be null if not provided
    val phoneNumber: String?, // Contact number, can be null if not provided
    val isBookmarked: Int // Bookmark status, 1 for bookmarked, 0 for not
)
