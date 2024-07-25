package com.example.lokalassignment.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Database schema version. Increase the version number when schema changes occur.
@Database(entities = [BookmarkedJob::class], version = 2)
abstract class BookmarkedJobDatabase : RoomDatabase() {

    // Abstract method to access DAO.
    abstract fun bookmarkedJobDAO(): BookmarkedJobsDAO

    companion object {
        // Migration object for upgrading the database from version 1 to version 2.
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Adds a new column 'isBookmarked' to the 'bookmarkedJobs' table.
                database.execSQL("ALTER TABLE bookmarkedJobs ADD COLUMN isBookmarked INTEGER NOT NULL DEFAULT 0")
            }
        }

        // Volatile ensures that the instance is immediately visible to all threads.
        @Volatile
        private var INSTANCE: BookmarkedJobDatabase? = null

        /**
         * Returns the singleton instance of the database. If it does not exist, it creates a new one.
         *
         * @param context The application context used to create the database instance.
         * @return The singleton instance of [BookmarkedJobDatabase].
         */
        fun getDatabase(context: Context): BookmarkedJobDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkedJobDatabase::class.java,
                    "bookmarkedJobsDB"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
