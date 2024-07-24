package com.example.lokalassignment.interfaces

import com.example.lokalassignment.model.ApiResponse
import com.example.lokalassignment.model.Job
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service interface for fetching job data from the API.
 */
interface ApiService {

    /**
     * Fetches a list of jobs.
     *
     * @param page The page number to retrieve.
     * @return A [Call] containing the API response with a list of jobs.
     */
    @GET("common/jobs")
    fun getJobs(@Query("page") page: Int): Call<ApiResponse<MutableList<Job>>>
}
