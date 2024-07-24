package com.example.lokalassignment.interfaces

import com.example.lokalassignment.model.ApiResponse
import com.example.lokalassignment.model.Job
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("common/jobs")
    fun getJobs(@Query("page") page: Int): Call<ApiResponse<MutableList<Job>>>
}
