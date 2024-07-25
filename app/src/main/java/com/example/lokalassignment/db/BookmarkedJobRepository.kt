package com.example.lokalassignment.db

import androidx.lifecycle.LiveData

/**
 * Repository class that abstracts access to data sources for bookmarked jobs.
 * This class is responsible for handling data operations and managing the
 * interaction between the ViewModel and the data layer.
 *
 * @property bookmarkedJobsDAO The DAO for accessing bookmarked jobs in the database.
 */
class BookmarkedJobRepository(private val bookmarkedJobsDAO: BookmarkedJobsDAO) {

    // LiveData holding the list of all bookmarked jobs.
    val getJobs: LiveData<MutableList<BookmarkedJob>> = bookmarkedJobsDAO.getJobs()

    /**
     * Inserts a job into the list of bookmarked jobs.
     * This operation is performed on a background thread.
     *
     * @param bookmarkedJob The job to be bookmarked.
     */
    suspend fun insertJob(bookmarkedJob: BookmarkedJob) {
        bookmarkedJobsDAO.insertJob(bookmarkedJob)
    }
}
