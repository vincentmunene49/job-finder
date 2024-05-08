package com.example.jobfinder.admin.post.domain

import Resource
import com.example.jobfinder.admin.post.data.model.Job
import kotlinx.coroutines.flow.Flow

interface PostJobRepository {
   suspend fun postJob(job: Job, jobImage: ByteArray): Flow<Resource<Job>>

}