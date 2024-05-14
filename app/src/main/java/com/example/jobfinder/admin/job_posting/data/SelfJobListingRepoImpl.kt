package com.example.jobfinder.admin.job_posting.data

import Resource
import com.example.jobfinder.admin.job_posting.domain.SelfJobListingRepo
import com.example.jobfinder.admin.post.data.model.Job
import com.example.jobfinder.common.util.JOB_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class SelfJobListingRepoImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : SelfJobListingRepo {
    override suspend fun getJobs(): Flow<Resource<List<Job>>> = flow {
        emit(Resource.Loading())
        try {
            val jobs = firestore.collection(JOB_COLLECTION)
                .whereEqualTo("jobPosterId", firebaseAuth.currentUser?.uid)
                .get()
                .await()
                .toObjects(Job::class.java)
            emit(Resource.Success(jobs))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))

        }
    }

    override suspend fun deleteJob(jobId: String): Flow<Resource<Job>> = flow {
        emit(Resource.Loading())

        try {
            val job = firestore.collection(JOB_COLLECTION)
                .document(jobId)
                .get()
                .await()
                .toObject(Job::class.java)
            job?.let {
                firestore.collection(JOB_COLLECTION)
                    .document(jobId)
                    .delete()
                    .await()
                emit(Resource.Success(job))
            } ?: emit(Resource.Error("Job not found"))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}