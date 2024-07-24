package com.example.lokalassignment.db

import androidx.room.Database
import androidx.room.RoomDatabase

// Version is to be changed when there is some change in the database, i.e, in a new update of the app.
@Database(entities = [BookmarkedJob::class], version = 1)
abstract class BookmarkedJobDatabase : RoomDatabase() {
    abstract fun bookmarkedJobDAO(): BookmarkedJobsDAO
}