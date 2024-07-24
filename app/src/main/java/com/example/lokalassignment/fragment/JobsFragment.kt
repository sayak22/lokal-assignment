package com.example.lokalassignment.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lokalassignment.R
import com.example.lokalassignment.adapter.JobsAdapter
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
    private val BASE_URL = "https://testapi.getlokalapp.com/"

    // Data
    private val jobList: MutableList<Job> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_jobs, container, false)

        // Initialize RecyclerView
        initializeRecyclerView(rootView)

        // Initialize progress dialog
        initializeProgressDialog()

        // Fetch jobs data using coroutines
        fetchJobs()

        return rootView
    }

    private fun initializeRecyclerView(rootView: View) {
        recyclerView = rootView.findViewById(R.id.jobListRV)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        jobsAdapter = JobsAdapter(jobList)
        recyclerView.adapter = jobsAdapter
    }

    private fun initializeProgressDialog() {
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Loading jobs...")
        progressDialog.setCancelable(false)
    }

    private fun fetchJobs() {
        lifecycleScope.launch {
            try {
                progressDialog.show()
                val response = fetchJobsFromApi()
                if (response.isSuccessful) {
                    jobList.clear()
                    response.body()?.results?.let { jobList.addAll(it) }
                    jobsAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                handleException(e)
            } finally {
                progressDialog.dismiss()
            }
        }
    }

    private suspend fun fetchJobsFromApi(): retrofit2.Response<ApiResponse<MutableList<Job>>> {
        return withContext(Dispatchers.IO) {
            val retrofit = createRetrofitInstance()
            retrofit.getJobs(1).execute()
        }
    }

    private fun createRetrofitInstance(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun handleException(e: Exception) {
        Toast.makeText(requireContext(), "INTERNAL ERROR -> ${e.message}", Toast.LENGTH_SHORT).show()
        Log.e("CatchException", e.message.toString())
    }
}
