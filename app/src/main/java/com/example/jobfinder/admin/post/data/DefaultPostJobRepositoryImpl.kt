package com.example.jobfinder.admin.post.data

import Resource
import com.example.jobfinder.admin.post.data.model.Job
import com.example.jobfinder.admin.post.domain.PostJobRepository
import com.example.jobfinder.common.util.JOB_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class DefaultPostJobRepositoryImpl @Inject constructor(
    private val dataBase: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,
    private val auth: FirebaseAuth
) : PostJobRepository {
    override suspend fun postJob(job: Job, jobImage: ByteArray): Flow<Resource<Job>> = flow {
        emit(Resource.Loading())
        coroutineScope {
            try {
                val companyLogoUpload = async {
                    val imageRef =
                        firebaseStorage.reference.child("images/companyLogos/${UUID.randomUUID()}")
                    imageRef.putBytes(jobImage).await()
                    imageRef.downloadUrl.await().toString()
                }
                val companyLogoUrl = companyLogoUpload.await()
                val jobPosterId = auth.currentUser?.uid ?: ""

                val jobWithImage = job.copy(companyLogo = companyLogoUrl, jobPosterId = jobPosterId)
                val jobUpload = async {
                    val result = dataBase.collection(JOB_COLLECTION).add(jobWithImage).await()
                    val jobId = result.id
                    val jobWithId = jobWithImage.copy(jobId = jobId)
                    result.set(jobWithId).await()
                    jobWithId
                }

                val jobWithId = jobUpload.await()

                emit(Resource.Success(jobWithId))
            } catch (e: Exception) {
                if (e is CancellationException) {
                    throw e
                }
                emit(Resource.Error("☹\uFE0F \n ${e.message}"))
            }
        }
    }


}
