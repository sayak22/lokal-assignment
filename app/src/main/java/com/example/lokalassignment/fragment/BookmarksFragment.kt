package com.example.lokalassignment.fragment

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lokalassignment.R
import com.example.lokalassignment.adapter.BookmarksAdapter
import com.example.lokalassignment.db.BookmarkedJob
import com.example.lokalassignment.db.BookmarkedJobViewModel
import com.example.lokalassignment.model.Job
import kotlinx.coroutines.launch

class BookmarksFragment : Fragment() {

    // UI Components
    private lateinit var bookmarksAdapter: BookmarksAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressDialog: ProgressDialog

    // ViewModel for managing bookmarked jobs
    private lateinit var bookmarkedJobViewModel: BookmarkedJobViewModel

    // Data: List of bookmarked jobs
    private var bookmarkList: MutableList<Job> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_bookmarks, container, false)

        // Initialize RecyclerView
        initializeRecyclerView(rootView)

        // Initialize progress dialog
        initializeProgressDialog()

        // Fetch bookmarked jobs using coroutines
        fetchBookmarks()

        return rootView
    }

    private fun initializeRecyclerView(rootView: View) {
        recyclerView = rootView.findViewById(R.id.bookmarkedJobListRV)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookmarksAdapter = BookmarksAdapter(bookmarkList)
        recyclerView.adapter = bookmarksAdapter

        // Initialize the ViewModel for bookmarked jobs
        bookmarkedJobViewModel = ViewModelProvider(this)[BookmarkedJobViewModel::class.java]
    }

    private fun initializeProgressDialog() {
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Loading jobs...")
        progressDialog.setCancelable(false)
    }

    private fun fetchBookmarks() {
        lifecycleScope.launch {
            // Observe changes in bookmarked jobs LiveData
            bookmarkedJobViewModel.getJobs.observe(viewLifecycleOwner, Observer { data ->
                // Map BookmarkedJob objects to Job objects
                bookmarkList = data.map { bookmarkedJob ->
                    Job(
                        id = bookmarkedJob.id,
                        title = bookmarkedJob.title,
                        primaryDetails = Job.PrimaryDetails(
                            destination = bookmarkedJob.destination,
                            salary = bookmarkedJob.salary
                        ),
                        phoneNumber = bookmarkedJob.phoneNumber
                    )
                }.toMutableList()

                // Update the adapter with the new data
                bookmarksAdapter.setData(bookmarkList)
            })
        }
    }
}
