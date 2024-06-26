package com.example.jobfinder.user.apply.data

import Resource
import android.net.Uri
import androidx.core.net.toUri
import com.example.jobfinder.common.util.APPLICATIONS
import com.example.jobfinder.user.apply.data.model.ApplicationItem
import com.example.jobfinder.user.apply.domain.ApplicationRepository
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

class ApplicationRepositoryDefaultImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val auth: FirebaseAuth
) : ApplicationRepository {
    override suspend fun applyForJob(
        applicationItem: ApplicationItem,
        cvAttachment: ByteArray,
        coverLetter: ByteArray
    ): Flow<Resource<ApplicationItem>> = flow {
        emit(Resource.Loading())
        try {

            coroutineScope {
                val cvAttachmentUpload = async {
                    val cvRef = storage.reference.child("cv/${UUID.randomUUID()}")
                    cvRef.putBytes(cvAttachment).await()
                    cvRef.downloadUrl.await().toString()
                }
                val cvUrl = cvAttachmentUpload.await()

                val coverLetterUpload = async {
                    val coverLetterRef = storage.reference.child("coverLetter/${UUID.randomUUID()}")
                    coverLetterRef.putBytes(coverLetter).await()
                    coverLetterRef.downloadUrl.await().toString()
                }
                val coverLetterUrl = coverLetterUpload.await()

                //get user id
                val currentUserId = auth.currentUser?.uid ?: ""

                // Add the application to Firestore
                val uploadApplicationItem = applicationItem.copy(
                    userId = currentUserId,
                    cvAttachment = cvUrl,
                    coverLetter = coverLetterUrl
                )


                val jobUpload = async {
                    val result = firestore.collection(APPLICATIONS).add(applicationItem).await()
                    val applicationId = result.id
                    val applicationWithId = uploadApplicationItem.copy(id = applicationId)
                    result.set(applicationWithId).await()
                    applicationWithId
                }

                val jobUploadWithId = jobUpload.await()

                emit(Resource.Success(jobUploadWithId))
            }
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error("☹\uFE0F \n ${e.message}"))
        }
    }

    override suspend fun checkIfAlreadyApplied(jobId: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val currentUserId = auth.currentUser?.uid ?: ""
            val result = firestore.collection(APPLICATIONS)
                .whereEqualTo("userId", currentUserId)
                .whereEqualTo("jobId", jobId)
                .get()
                .await()

            val isApplied = result.size() > 0
            emit(Resource.Success(isApplied))
        } catch (e: Exception) {
            emit(Resource.Error("☹\uFE0F \n ${e.message}"))
        }

    }

}