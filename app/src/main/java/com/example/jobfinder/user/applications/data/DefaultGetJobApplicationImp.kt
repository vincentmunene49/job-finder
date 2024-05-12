package com.example.jobfinder.user.applications.data

import Resource
import android.app.Application
import com.example.jobfinder.admin.post.data.model.Job
import com.example.jobfinder.common.util.APPLICATIONS
import com.example.jobfinder.common.util.JOB_COLLECTION
import com.example.jobfinder.user.applications.domain.GetJobApplications
import com.example.jobfinder.user.apply.data.model.ApplicationItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class DefaultGetJobApplicationImp @Inject constructor(
    private val dataBase: FirebaseFirestore,
    private val auth: FirebaseAuth
) : GetJobApplications {
    override suspend fun getJobsAppliedFor(): Flow<Resource<List<Job>>> = flow {

        emit(Resource.Loading())

        try {
            val currentUserId = auth.currentUser?.uid ?: ""
            val applicationResult = dataBase.collection(APPLICATIONS)
                .whereEqualTo("userId", currentUserId)
                .get()
                .await()
            val applications = applicationResult.toObjects(ApplicationItem::class.java)
            val jobs = mutableListOf<Job>()
            for (application in applications) {
                val jobId = application.jobId
                val jobResult = dataBase.collection(JOB_COLLECTION)
                    .document(jobId ?: "")
                    .get()
                    .await()
                val job = jobResult.toObject(Job::class.java)
                if (job != null) {
                    jobs.add(job)
                }
            }
            emit(Resource.Success(jobs))
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error("☹\uFE0F \n ${e.message}"))
        }

    }


}