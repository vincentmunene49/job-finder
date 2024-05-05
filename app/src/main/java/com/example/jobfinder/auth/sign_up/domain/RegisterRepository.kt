package com.example.jobfinder.auth.sign_up.domain

import Resource
import User
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(
        email: String,
        password: String,
        firstName:String,
        lastName:String,
        isAdmin:Boolean
    ): Flow<Resource<User>>

}