package com.example.lokalassignment.fragment

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lokalassignment.R
import com.example.lokalassignment.adapter.BookmarksAdapter
import com.example.lokalassignment.adapter.JobsAdapter
import com.example.lokalassignment.db.BookmarkedJob
import com.example.lokalassignment.db.BookmarkedJobDatabase
import com.example.lokalassignment.model.Job
import com.example.lokalassignment.viewModel.BookmarkedJobsViewModel
import com.example.lokalassignment.viewModel.BookmarkedJobsViewModelFactory
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class BookmarksFragment : Fragment() {


    private val dao by lazy {
        BookmarkedJobDatabase.getDatabase(requireContext()).bookmarkedJobDAO()
    }

    private val viewModel: BookmarkedJobsViewModel by viewModels {
        BookmarkedJobsViewModelFactory(dao)
    }

    // UI Components
    private lateinit var bookmarksAdapter: BookmarksAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressDialog: ProgressDialog

//    private lateinit var bookmarkedJobsViewModel: BookmarkedJobsViewModel

    // Data
    private var bookmarkList: MutableList<Job> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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

        // Fetch jobs data using coroutines
        fetchBookmarks()
        return rootView
    }

    private fun initializeRecyclerView(rootView: View) {
        recyclerView = rootView.findViewById(R.id.bookmarkedJobListRV)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookmarksAdapter = BookmarksAdapter(bookmarkList)
        recyclerView.adapter = bookmarksAdapter
    }

    private fun initializeProgressDialog() {
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Loading jobs...")
        progressDialog.setCancelable(false)
    }

    private fun fetchBookmarks() {
        lifecycleScope.launch {
            viewModel.jobs.observe(viewLifecycleOwner, Observer { data ->
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

                bookmarksAdapter.setData(bookmarkList)
            })

        }
    }
}