package com.example.lokalassignment.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for managing BookmarkedJob data in the UI.
 * Uses AndroidViewModel to access application context for database operations.
 */
class BookmarkedJobViewModel(application: Application) : AndroidViewModel(application) {

    // LiveData holding the list of bookmarked jobs
    val getJobs: LiveData<MutableList<BookmarkedJob>>

    // Repository handling data operations
    private val repository: BookmarkedJobRepository

    // Initialization block
    init {
        // Initialize the DAO and repository
        val bookmarkedJobsDao = BookmarkedJobDatabase.getDatabase(application).bookmarkedJobDAO()
        repository = BookmarkedJobRepository(bookmarkedJobsDao)

        // Retrieve jobs from repository
        getJobs = repository.getJobs
    }

    /**
     * Inserts a new job into the database.
     * This operation is performed on a background thread.
     */
    fun insertJob(bookmarkedJob: BookmarkedJob) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertJob(bookmarkedJob)
        }
    }
}
