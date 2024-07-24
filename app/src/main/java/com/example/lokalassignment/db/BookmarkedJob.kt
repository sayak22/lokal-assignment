package com.example.lokalassignment.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarkedJobs")
data class BookmarkedJob(

    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val title: String?,
    val destination: String?,
    val salary: String?,
    val phoneNumber: String?
)
