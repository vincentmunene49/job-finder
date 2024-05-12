package com.example.jobfinder.auth.sign_up.data

import com.example.jobfinder.auth.sign_up.domain.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import Resource
import User
import com.example.jobfinder.common.util.ADMIN_COLLECTION
import com.example.jobfinder.common.util.USER_COLLECTION
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class DefaultRegisterRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : RegisterRepository {
    override suspend fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        isAdmin: Boolean
    ): Flow<Resource<User>> = flow {

        emit(Resource.Loading())
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = User(
                firstName = firstName,
                lastName = lastName,
                email = email
            )
            val collection = if (isAdmin) ADMIN_COLLECTION else USER_COLLECTION
            if (result.user != null) {
                val userUpload = user.copy(userId = result.user!!.uid)
                firebaseFirestore.collection(collection)
                    .document(result.user!!.uid)
                    .set(userUpload)
                    .await()
                emit(Resource.Success(user))
            } else {
                emit(Resource.Error("☹\uFE0F \n Error creating account"))
            }

        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error("☹\uFE0F \n ${e.message}"))
        }

    }

}