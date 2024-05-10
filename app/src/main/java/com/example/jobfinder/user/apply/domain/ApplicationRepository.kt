package com.example.jobfinder.user.apply.domain

import Resource
import android.net.Uri
import com.example.jobfinder.user.apply.data.model.ApplicationItem
import kotlinx.coroutines.flow.Flow

interface ApplicationRepository {
    suspend fun applyForJob(
        applicationItem: ApplicationItem,
        cvAttachment: ByteArray,
        coverLetter: ByteArray
    ): Flow<Resource<ApplicationItem>>
}