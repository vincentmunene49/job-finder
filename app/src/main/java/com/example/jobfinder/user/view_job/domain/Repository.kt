package com.example.jobfinder.user.view_job.domain

import Resource
import com.example.jobfinder.admin.post.data.model.Job
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getJobById(jobId: String): Flow<Resource<Job>>
}