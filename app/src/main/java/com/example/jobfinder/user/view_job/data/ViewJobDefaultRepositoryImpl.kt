package com.example.jobfinder.user.view_job.data

import Resource
import com.example.jobfinder.admin.post.data.model.Job
import com.example.jobfinder.common.util.APPLICATIONS
import com.example.jobfinder.common.util.JOB_COLLECTION
import com.example.jobfinder.user.view_job.domain.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ViewJobDefaultRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    val auth: FirebaseAuth
) : Repository {
    override suspend fun getJobById(jobId: String): Flow<Resource<Job>> = flow {
        emit(Resource.Loading())
        try {
            val job = firestore.collection(JOB_COLLECTION).document(jobId).get().await()
                .toObject(Job::class.java)
            emit(Resource.Success(job!!))
        } catch (e: Exception) {
            emit(Resource.Error("☹\uFE0F \n ${e.message}"))
        }
    }

    override suspend fun withdrawApplication(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        try {
            val currentUser = auth.currentUser?.uid
            val querySnapshot = firestore.collection(APPLICATIONS).whereEqualTo("userId", currentUser).get().await()

            for (document in querySnapshot.documents) {
                document.reference.delete().await()
            }

            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error("☹\uFE0F \n ${e.message}"))
        }

    }
}