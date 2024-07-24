package com.example.lokalassignment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lokalassignment.db.BookmarkedJob
import com.example.lokalassignment.db.BookmarkedJobsDAO
import kotlinx.coroutines.launch

class BookmarkedJobsViewModel(private val dao: BookmarkedJobsDAO) : ViewModel() {

    val jobs: LiveData<MutableList<BookmarkedJob>> = dao.getJobs()

    fun insertJob(job: BookmarkedJob) {
        viewModelScope.launch {
            dao.insertJob(job)
        }
    }

    fun updateJob(job: BookmarkedJob) {
        viewModelScope.launch {
            dao.updateJob(job)
        }
    }

    fun deleteJob(job: BookmarkedJob) {
        viewModelScope.launch {
            dao.deleteJob(job)
        }
    }
}
