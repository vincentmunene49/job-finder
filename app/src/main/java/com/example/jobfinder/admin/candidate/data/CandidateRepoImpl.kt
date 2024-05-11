package com.example.jobfinder.admin.candidate.data

import Resource
import com.example.jobfinder.admin.applications.data.model.ApplicationJobItem
import com.example.jobfinder.admin.candidate.domain.CandidateRepo
import com.example.jobfinder.admin.post.data.model.Job
import com.example.jobfinder.common.util.APPLICATIONS
import com.example.jobfinder.common.util.JOB_COLLECTION
import com.example.jobfinder.user.apply.data.model.ApplicationItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CandidateRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): CandidateRepo {
    override suspend fun getCandidateDetails(
        jobId: String?
    ): Flow<Resource<ApplicationJobItem>> = flow{
        emit(Resource.Loading())
        try {
            val jobResult = firestore.collection(APPLICATIONS)
                .document(jobId!!)
                .get()
                .await()

            val job = jobResult.toObject(ApplicationItem::class.java)
            val appliedJobId = job?.jobId
            val getJob = firestore.collection(JOB_COLLECTION)
                .document(appliedJobId!!)
                .get()
                .await()
            val jobDetails = getJob.toObject(Job::class.java)

            val appliedJobItem = ApplicationJobItem(
                applicationItem = job,
                job = jobDetails
            )


            emit(Resource.Success(appliedJobItem))

        } catch (e: Exception) {
            emit(Resource.Error("☹\uFE0F \n ${e.message}"))
        }
    }
}