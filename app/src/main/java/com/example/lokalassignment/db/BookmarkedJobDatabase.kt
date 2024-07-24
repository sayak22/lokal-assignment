package com.example.lokalassignment.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Version is to be changed when there is some change in the database, i.e, in a new update of the app.
@Database(entities = [BookmarkedJob::class], version = 1)
abstract class BookmarkedJobDatabase : RoomDatabase() {
    abstract fun bookmarkedJobDAO(): BookmarkedJobsDAO

    companion object {

        @Volatile
        private var INSTANCE: BookmarkedJobDatabase? = null

        fun getDatabase(context: Context): BookmarkedJobDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BookmarkedJobDatabase::class.java,
                        "bookmarkedJobsDB"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}