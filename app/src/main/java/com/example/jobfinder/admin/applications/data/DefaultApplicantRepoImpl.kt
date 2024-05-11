package com.example.jobfinder.admin.applications.data

import Resource
import User
import com.example.jobfinder.admin.applications.data.model.ApplicationJobItem
import com.example.jobfinder.admin.applications.domain.ApplicantsRepo
import com.example.jobfinder.common.util.APPLICATIONS
import com.example.jobfinder.common.util.USER_COLLECTION
import com.example.jobfinder.user.apply.data.model.ApplicationItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DefaultApplicantRepoImpl @Inject constructor(
    private val database: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ApplicantsRepo {
    override suspend fun getJobApplicants(): Flow<Resource<List<ApplicationJobItem>>> = flow {
        emit(Resource.Loading())
        try {
            val currentUser = auth.currentUser?.uid ?: ""
            val applicationResult = database.collection(APPLICATIONS)
                .whereEqualTo("jobPosterId", currentUser)
                .get()
                .await()

            val applicants = applicationResult.toObjects(ApplicationItem::class.java)
            val usersWithJobs = mutableListOf<ApplicationJobItem>()
            for (applicant in applicants) {
                val userId = applicant.userId
                val userResult = database.collection(USER_COLLECTION)
                    .document(userId ?: "")
                    .get()
                    .await()
                val user = userResult.toObject(User::class.java)
                if (user != null) {
                    usersWithJobs.add(ApplicationJobItem(user = user, applicationItem = applicant))
                }
            }
            emit(Resource.Success(usersWithJobs))
        } catch (e: Exception) {
            emit(Resource.Error("☹\uFE0F \n ${e.message}"))
        }
    }
}