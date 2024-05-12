package com.example.jobfinder.profile.data

import Resource
import User
import com.example.jobfinder.common.util.USER_COLLECTION
import com.example.jobfinder.profile.domain.ProfileScreenRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID

class DefaultProfileRepoImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ProfileScreenRepo {
    override suspend fun getProfileData(): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val user =
                firestore.collection(USER_COLLECTION).document(firebaseAuth.currentUser!!.uid).get()
                    .await().toObject(User::class.java)
            emit(Resource.Success(user!!))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }

    override suspend fun updateProfileImage(image: ByteArray): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        coroutineScope {
            try {
                val currentUser = firebaseAuth.currentUser?.uid
                val userDocument = firestore.collection(USER_COLLECTION).document(currentUser!!)
                val imageUpload = async {
                    val imageRef =
                        storage.reference.child("images/profileImages/${UUID.randomUUID()}")
                    imageRef.putBytes(image).await()
                    imageRef.downloadUrl.await().toString()
                }
                val imageUrl = imageUpload.await()
                userDocument.update("imagePath", imageUrl).await()
                emit(Resource.Success(imageUrl))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }

    }

    override suspend fun logout(): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading())
            try {
                firebaseAuth.signOut()
                emit(Resource.Success(Unit))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An error occurred"))
            }
        }
    }
}