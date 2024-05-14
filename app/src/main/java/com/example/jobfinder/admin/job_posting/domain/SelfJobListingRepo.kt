package com.example.jobfinder.admin.job_posting.domain

import Resource
import com.example.jobfinder.admin.post.data.model.Job
import kotlinx.coroutines.flow.Flow

interface SelfJobListingRepo {
    suspend fun getJobs():Flow<Resource<List<Job>>>

    suspend fun deleteJob(jobId: String): Flow<Resource<Job>>
}