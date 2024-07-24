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
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JobsFragment : Fragment() {

    private lateinit var jobsAdapter: JobsAdapter
    private lateinit var recyclerView: RecyclerView
    private var jobList: MutableList<Job> = mutableListOf()
    private lateinit var progressDialog: ProgressDialog // Declare the progress dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_jobs, container, false)

        recyclerView = rootView.findViewById(R.id.jobListRV)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        jobsAdapter = JobsAdapter(jobList)
        recyclerView.adapter = jobsAdapter

        // Initialize the progress dialog
        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Loading jobs...")
        progressDialog.setCancelable(false)

        // Fetch jobs data
        fetchJobs()

        return rootView
    }

    private fun fetchJobs() {
        try {
            progressDialog.show() // Show the progress dialog

            val retrofit = Retrofit.Builder()
                .baseUrl("https://testapi.getlokalapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)

            retrofit.getJobs(1).enqueue(object : Callback<ApiResponse<MutableList<Job>>> {
                override fun onResponse(call: Call<ApiResponse<MutableList<Job>>>, response: Response<ApiResponse<MutableList<Job>>>) {
                    progressDialog.dismiss()
                    if (response.isSuccessful) {
                        jobList.clear()
                        response.body()?.results?.let { jobList.addAll(it) }
                        Log.d("JOBLIST","JOBLIST -> $jobList")
                        jobsAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<ApiResponse<MutableList<Job>>>, t: Throwable) {
                    progressDialog.dismiss() // Dismiss the progress dialog on failure too
                    Toast.makeText(requireContext(), "ERROR -> ${t.message}", Toast.LENGTH_SHORT).show()
                    Log.e("RequestError", t.message.toString())
                }
            })
        } catch (e: Exception) {
            progressDialog.dismiss() // Dismiss the progress dialog on exception
            Toast.makeText(requireContext(), "INTERNAL ERROR -> ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("CatchException", e.message.toString())
        }
    }
}
