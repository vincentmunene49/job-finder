package com.example.jobfinder.profile.domain

import Resource
import User
import kotlinx.coroutines.flow.Flow

interface ProfileScreenRepo {
    suspend fun getProfileData(): Flow<Resource<User>>


    suspend fun updateProfileImage(image: String): Flow<Resource<String>>

    suspend fun logout(): Flow<Resource<Unit>>
}