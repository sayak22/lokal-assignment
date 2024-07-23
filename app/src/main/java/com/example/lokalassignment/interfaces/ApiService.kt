package com.example.lokalassignment.interfaces

import com.example.lokalassignment.model.Job
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApiService {
    @GET("common/jobs")
    suspend fun getJobs(@Query("page") page: Int): List<Job>
}