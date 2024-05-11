package com.example.jobfinder.admin.applications.domain

import Resource
import User
import com.example.jobfinder.admin.applications.data.model.ApplicationJobItem
import kotlinx.coroutines.flow.Flow

interface ApplicantsRepo {

    suspend fun getJobApplicants ():Flow<Resource<List<ApplicationJobItem>>>
}