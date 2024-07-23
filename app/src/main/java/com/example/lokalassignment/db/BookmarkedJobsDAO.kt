package com.example.lokalassignment.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BookmarkedJobsDAO {

    @Insert
    suspend fun insertJob(bookmarkedJob: BookmarkedJob)

    @Update
    suspend fun updateJob(bookmarkedJob: BookmarkedJob)

    @Delete
    suspend fun deleteJob(bookmarkedJob: BookmarkedJob)

    // LiveData is always executed in background thread.
    @Query("SELECT * FROM bookmarkedJobs")
    fun getJobs(): LiveData<List<BookmarkedJob>>
}
