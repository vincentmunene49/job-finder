package com.example.jobfinder.user.home.data

import Resource
import com.example.jobfinder.admin.post.data.model.Job
import com.example.jobfinder.common.util.JOB_COLLECTION
import com.example.jobfinder.user.home.domain.Repository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class RepositoryDefaultImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : Repository {
    override suspend fun getAllJobs(): Flow<Resource<List<Job>>> = flow {
        emit(Resource.Loading())
        try {
            val jobs = firestore.collection(JOB_COLLECTION).get().await().toObjects(Job::class.java)
            emit(Resource.Success(jobs))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error("☹\uFE0F \n ${e.message}"))
        }

    }

    override suspend fun searchJobs(
        jobTitle: String
    ): Flow<Resource<List<Job>>> = flow {

        emit(Resource.Loading())
        try {
            val startAt = if (jobTitle.isNotEmpty()) jobTitle[0].toString() else ""
            val result = firestore.collection(JOB_COLLECTION)
                .orderBy("jobTitle")
                .startAt(startAt)
                .get()
                .await()
            val jobs = result.toObjects(Job::class.java)
            if (jobs.isNotEmpty()) {
                emit(Resource.Success(jobs))

            } else {
                emit(Resource.Success(emptyList()))
            }
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error("☹\uFE0F \n ${e.message}"))
        }

    }
}