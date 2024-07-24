package com.example.lokalassignment.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarkedJobs")
data class BookmarkedJob(

    @PrimaryKey
    val id: Long,
    val title: String,
    val place: String,
    val salary: String,
    val whatsappNo: String
)
