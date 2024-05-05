package com.example.jobfinder.auth.login.domain

import Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun login(
        email: String,
        password: String
    ): Flow<Resource<Pair<FirebaseUser, String>>>
}