package com.example.jobfinder.user.home.domain

import Resource
import com.example.jobfinder.admin.post.data.model.Job
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getAllJobs(): Flow<Resource<List<Job>>>

    suspend fun searchJobs(jobTitle: String): Flow<Resource<List<Job>>>
}