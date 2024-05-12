package com.example.jobfinder.user.applications.domain

import Resource
import com.example.jobfinder.admin.post.data.model.Job
import kotlinx.coroutines.flow.Flow

interface GetJobApplications {
    suspend fun getJobsAppliedFor(): Flow<Resource<List<Job>>>

}