package com.example.lokalassignment.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lokalassignment.db.BookmarkedJobsDAO

class BookmarkedJobsViewModelFactory(private val dao: BookmarkedJobsDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkedJobsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookmarkedJobsViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
