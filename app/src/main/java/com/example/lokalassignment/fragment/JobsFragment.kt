package com.example.lokalassignment.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lokalassignment.R
import com.example.lokalassignment.adapter.JobsAdapter
import com.example.lokalassignment.db.BookmarkedJob
import com.example.lokalassignment.db.BookmarkedJobDatabase
import com.example.lokalassignment.db.BookmarkedJobViewModel
import com.example.lokalassignment.interfaces.ApiService
import com.example.lokalassignment.model.ApiResponse
import com.example.lokalassignment.model.Job
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JobsFragment : Fragment() {

    // UI components
    private lateinit var jobsAdapter: JobsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressDialog: ProgressDialog

    // Data and ViewModel
    private lateinit var bookmarkedJobViewModel: BookmarkedJobViewModel
    private val jobList: MutableList<Job> = mutableListOf()
    private var bookmarkList: MutableList<Job> = mutableListOf()

    // API
    private val BASE_URL = "https://testapi.getlokalapp.com/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_jobs, container, false)

        // Initialize UI components and data
        initializeRecyclerView(rootView)
        initializeProgressDialog()
        fetchJobs()

        return rootView
    }

    /**
     * Initializes the RecyclerView with its adapter and layout manager.
     */
    private fun initializeRecyclerView(rootView: View) {
        recyclerView = rootView.findViewById(R.id.jobListRV)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        jobsAdapter = JobsAdapter(jobList)
        recyclerView.adapter = jobsAdapter

        // ViewModel for bookmarked jobs
        bookmarkedJobViewModel = ViewModelProvider(this)[BookmarkedJobViewModel::class.java]
    }

    /**
     * Sets up the ProgressDialog for loading indication.
     */
    private fun initializeProgressDialog() {
        progressDialog = ProgressDialog(requireContext()).apply {
            setMessage("Loading jobs...")
            setCancelable(false)
        }
    }

    /**
     * Fetches job data from the API.
     */
    private fun fetchJobs() {
        lifecycleScope.launch {
            try {
                progressDialog.show()
                val response = fetchJobsFromApi()
                if (response.isSuccessful) {
                    jobList.clear()
                    response.body()?.results?.let { jobList.addAll(it) }
                    checkBookmarkedJobsAndApplyAdapter(jobList)
                }
            } catch (e: Exception) {
                handleException(e)
            } finally {
                progressDialog.dismiss()
            }
        }
    }

    /**
     * Checks if jobs are bookmarked and updates the adapter.
     */
    private fun checkBookmarkedJobsAndApplyAdapter(jobList: MutableList<Job>) {
        lifecycleScope.launch {
            bookmarkedJobViewModel.getJobs.observe(viewLifecycleOwner, Observer { data ->
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

                // Update jobs with bookmark status
                jobList.forEach { job ->
                    if (bookmarkList.any { it.id == job.id }) {
                        job.isBookmarked = 1
                    }
                }

                // Notify adapter about data changes
                jobsAdapter.notifyDataSetChanged()
            })
        }
    }

    /**
     * Fetches jobs from the API using Retrofit.
     */
    private suspend fun fetchJobsFromApi(): retrofit2.Response<ApiResponse<MutableList<Job>>> {
        return withContext(Dispatchers.IO) {
            val retrofit = createRetrofitInstance()
            retrofit.getJobs(1).execute()
        }
    }

    /**
     * Creates a Retrofit instance for API calls.
     */
    private fun createRetrofitInstance(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    /**
     * Handles exceptions by showing a Toast and logging the error.
     */
    private fun handleException(e: Exception) {
        Toast.makeText(requireContext(), "INTERNAL ERROR -> ${e.message}", Toast.LENGTH_SHORT).show()
        Log.e("CatchException", e.message.toString())
    }
}
