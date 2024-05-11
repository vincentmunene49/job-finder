package com.example.jobfinder.admin.candidate.domain

import Resource
import com.example.jobfinder.admin.applications.data.model.ApplicationJobItem
import kotlinx.coroutines.flow.Flow

interface CandidateRepo {
    suspend fun getCandidateDetails(
         jobId:String?
    ):Flow<Resource<ApplicationJobItem>>
}