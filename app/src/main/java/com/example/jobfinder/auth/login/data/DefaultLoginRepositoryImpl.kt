package com.example.jobfinder.auth.login.data

import Resource
import com.example.jobfinder.auth.login.domain.LoginRepository
import com.example.jobfinder.common.util.ADMIN_COLLECTION
import com.example.jobfinder.common.util.USER_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class DefaultLoginRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : LoginRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Flow<Resource<Pair<FirebaseUser, String>>> = flow {
        emit(Resource.Loading())
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            if (result.user != null) {
                val uid = result.user!!.uid
                Timber.tag("LoginRepository").d("UID: $uid")

                val adminDocument =
                    firebaseFirestore.collection(ADMIN_COLLECTION).document(uid).get().await()
                if (adminDocument.exists()) {
                    emit(Resource.Success(Pair(result.user!!, "admin")))
                } else {
                    val userDocument =
                        firebaseFirestore.collection(USER_COLLECTION).document(uid).get().await()
                    if (userDocument.exists()) {
                        emit(Resource.Success(Pair(result.user!!, "user")))
                    }
                }
            } else {
                emit(Resource.Error("☹\uFE0F \nYou do not have an account. Please consider registering"))
            }
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emit(Resource.Error("☹\uFE0F \n ${e.message}"))
        }
    }

}